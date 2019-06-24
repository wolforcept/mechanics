package wolforce.mechanics.blocks.tiles;

import javax.annotation.Nullable;

import mechanics.ct.RecipeDryingTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.mechanics.MUtil;

public class TileDryingTable extends TileEntity implements ITickable {

	private ItemStackHandler inventory = new ItemStackHandler(4);
	private int[] charges = new int[inventory.getSlots()];

	{
		for (int i = 0; i < charges.length; i++) {
			charges[i] = 0;
		}
	}

	@Override
	public void update() {

		if(world.isRemote)
			return;
		
		for (int slot = 0; slot < charges.length; slot++) {
			ItemStack stack = inventory.getStackInSlot(slot);
			if (MUtil.isValid(stack)) {
				if (charges[slot] > 0) {
					charges[slot]--;
					if (charges[slot] == 0) {

						ItemStack result = RecipeDryingTable.getResult(stack);
						pop(slot);
						inventory.insertItem(slot, result, false);

						if (MUtil.isValid(RecipeDryingTable.getResult(result)))
							charges[slot] = RecipeDryingTable.getTime(stack);
						
						markDirty();
					}
				}
			}
		}
	}

	public ItemStack get(int slot) {
		return inventory.getStackInSlot(slot);
	}

	public boolean isFull() {
		return MUtil.getAvailableSlot(inventory) == -1;
	}

	public boolean insert(ItemStack stack) {
		int slot = MUtil.getAvailableSlot(inventory);
		if (slot < 0)
			return false;
		return insert(stack, slot);
	}

	private boolean insert(ItemStack stack, int slot) {
		if (!MUtil.isValid(RecipeDryingTable.getResult(stack)))
			return false;
		inventory.insertItem(slot, stack, false);
		charges[slot] = RecipeDryingTable.getTime(stack);
		return true;
	}

	public ItemStack pop(int slot) {
		charges[slot] = -1;
		return inventory.extractItem(slot, 64, false);
	}

	public void dropContents(World world, BlockPos pos) {
		for (int slot = 0; slot < inventory.getSlots(); slot++)
			MUtil.spawnItem(world, pos, inventory.extractItem(slot, 64, false));
	}

	//

	//

	// HAS DATA TO SAVE

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		for (int i = 0; i < charges.length; i++) {
			compound.setIntArray("charges", charges);
		}
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		charges = compound.getIntArray("charges");
	}

	//

	//

	// UPDATING VIA NET

	private IBlockState getState() {
		return world.getBlockState(pos);
	}

	@Override
	public void markDirty() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		super.markDirty();
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}

}
