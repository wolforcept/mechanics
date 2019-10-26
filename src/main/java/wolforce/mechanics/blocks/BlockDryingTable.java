package wolforce.mechanics.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.mechanics.Util;
import wolforce.mechanics.blocks.tiles.TileDryingTable;
import wolforce.mechanics.recipes.RecipeDryingTable;

public class BlockDryingTable extends Block implements ITileEntityProvider {

	static AxisAlignedBB bb = new AxisAlignedBB(0, 0, 0, 1, 8 / 16f, 1);

	public BlockDryingTable() {
		super(Material.WOOD);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileDryingTable();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {

		ItemStack held = player.getHeldItem(hand);
		ItemStack result = RecipeDryingTable.getResult(held);
		if (!Util.isValid(result))
			return true;

		TileEntity _tile = world.getTileEntity(pos);

		if (_tile != null && _tile instanceof TileDryingTable) {

			TileDryingTable tile = (TileDryingTable) _tile;
			if (Util.isValid(held) && !tile.isFull()) {
				ItemStack insertedStack = held.copy();
				insertedStack.setCount(1);
				tile.insert(insertedStack);
				held.shrink(1);
			}

		}
		return true;
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {

		if (world.isRemote)
			return;

		@SuppressWarnings("deprecation")
		RayTraceResult raytraceresult = net.minecraftforge.common.ForgeHooks.rayTraceEyes(player,
				((EntityPlayerMP) player).interactionManager.getBlockReachDistance() + 1);

		TileEntity _tile = world.getTileEntity(pos);

		if (raytraceresult == null || _tile == null || !(_tile instanceof TileDryingTable))
			return;

		TileDryingTable tile = (TileDryingTable) _tile;

		double xx = raytraceresult.hitVec.x - pos.getX();
		double zz = raytraceresult.hitVec.z - pos.getZ();

		if (xx < .5 && zz < .5)
			Util.spawnItem(world, pos.add(0, .5, 0), tile.pop(0));
		else if (zz < .5)
			Util.spawnItem(world, pos.add(0, .5, 0), tile.pop(1));
		else if (xx < .5)
			Util.spawnItem(world, pos.add(0, .5, 0), tile.pop(2));
		else
			Util.spawnItem(world, pos.add(0, .5, 0), tile.pop(3));

		tile.markDirty();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote) {
			TileEntity _tile = world.getTileEntity(pos);
			
			if (_tile != null && _tile instanceof TileDryingTable) 
				((TileDryingTable) _tile).dropContents(world, pos);
			
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return bb;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return bb;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return bb;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public boolean isTopSolid(IBlockState state) {
		return false;
	}

	// @Override
	// public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world,
	// BlockPos pos, IBlockState state,
	// int fortune) {
	//
	// TileEntity _tile = world.getTileEntity(pos);
	//
	// if (_tile != null && _tile instanceof TileDryingTable) {
	//
	// TileDryingTable tile = (TileDryingTable) _tile;
	//
	// for (int i = 0; i < tile.inventory.getSlots(); i++) {
	// ItemStack stack = tile.inventory.extractItem(i, 1, false);
	// drops.add(stack);
	// }
	// }
	// super.getDrops(drops, world, pos, state, fortune);
	// }
}
