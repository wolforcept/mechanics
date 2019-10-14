package wolforce.mechanics;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class MUtil {

	public static void setReg(Block block, String name) {
		block.setTranslationKey(Mechanics.MODID + "." + name);
		block.setRegistryName(MUtil.res(name));
	}

	public static void setReg(Item block, String name) {
		block.setTranslationKey(Mechanics.MODID + "." + name);
		block.setRegistryName(MUtil.res(name));
	}

	// ITEMSTACKS
	public static boolean isValid(ItemStack stack) {
		return stack != null && stack.getCount() > 0 && !stack.isEmpty() && !stack.getItem().equals(Items.AIR);
	}

	public static boolean equalExceptAmount(ItemStack stack1, ItemStack stack2) {
		return stack1.getItem() == stack2.getItem() && stack1.getMetadata() == stack2.getMetadata();
	}

	public static void spawnItem(World world, BlockPos pos, ItemStack stack, double... speeds) {
		spawnItem(world, new Vec3d(pos.getX() + .5, pos.getY(), pos.getZ() + .5), stack, speeds);
	}

	public static void spawnItem(World world, Vec3d pos, ItemStack stack, double... speeds) {
		spawnItem(world, pos, stack, 0, speeds);
	}

	/**
	 * default pickupDelay = 10 player throw pickupDelay = 40
	 */
	public static void spawnItem(World world, Vec3d pos, ItemStack stack, int pickupDelay, double... speeds) {
		if (!MUtil.isValid(stack))
			return;
		EntityItem entityitem = new EntityItem(world, pos.x, pos.y, pos.z, stack);
		if (speeds.length == 0) {
			entityitem.motionX = Math.random() * .4 - .2;
			entityitem.motionY = Math.random() * .2;
			entityitem.motionZ = Math.random() * .4 - .2;
		} else {
			entityitem.motionX = speeds[0];
			entityitem.motionY = speeds[1];
			entityitem.motionZ = speeds[2];
		}
		entityitem.setPickupDelay(pickupDelay);
		world.spawnEntity(entityitem);
	}

	public static ResourceLocation res(String string) {
		return new ResourceLocation(Mechanics.MODID, string);
	}

	/**
	 * @return an available slot or -1 if no slot is free
	 */
	public static int getAvailableSlot(ItemStackHandler inventory) {
		for (int i = 0; i < inventory.getSlots(); i++) {
			if (!isValid(inventory.getStackInSlot(i)))
				return i;
		}
		return -1;
	}

	public static String translateToLocal(String key) {
		if (I18n.canTranslate(key))
			return I18n.translateToLocal(key);
		else
			return I18n.translateToFallback(key);
	}

	public static ItemStack stateToStack(IBlockState state) {
		return new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
	}
}
