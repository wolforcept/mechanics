package wolforce.mechanics.items;

import mechanics.ct.RecipeMartlet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolforce.mechanics.MUtil;
import wolforce.mechanics.Mechanics;

@Mod.EventBusSubscriber(modid = Mechanics.MODID)
public class ItemMartlet extends ItemTool {

	public ItemMartlet(float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn) {
		super(attackDamageIn, attackSpeedIn, materialIn, RecipeMartlet.getInputBlocks());
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (MUtil.isValid(stack))
			stack.damageItem(1, entityLiving);
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}

	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		return RecipeMartlet.getInputBlocks().contains(state.getBlock());
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		if (MUtil.isValid(RecipeMartlet.getResult(MUtil.stateToStack(state))))
			return efficiency;
		return super.getDestroySpeed(stack, state);
	}

	//

	//

	@SubscribeEvent
	public static void onHarvest(BlockEvent.HarvestDropsEvent event) {
		if (event.getHarvester() != null && event.getHarvester().getHeldItem(event.getHarvester().getActiveHand())
				.getItem() instanceof ItemMartlet) {
			ItemStack result = RecipeMartlet.getResult(new ItemStack(event.getState().getBlock(), 1,
					event.getState().getBlock().getMetaFromState(event.getState())));
			System.out.println(result);
			if (MUtil.isValid(result)) {
				event.getDrops().clear();
				event.getDrops().add(result);
			}
			// List<ItemStack> drops = event.getDrops();
			// if (drops != null && drops.size() > 0) {
			// BlockPos pos = event.getPos();
			// IBlockState blockstate = event.getWorld().getBlockState(pos);
			// Block block = blockstate.getBlock();
			// if (block instanceof BlockGrass) {
			// drops.add(new ItemStack(Item.REGISTRY.getObject(new
			// ResourceLocation("minecraft", "wheat_seeds"))));
			// }
			// }
		}
	}
}
