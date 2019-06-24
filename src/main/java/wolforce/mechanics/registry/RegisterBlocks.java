package wolforce.mechanics.registry;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolforce.mechanics.MUtil;
import wolforce.mechanics.Main;
import wolforce.mechanics.Mechanics;

@Mod.EventBusSubscriber(modid = Mechanics.MODID)
public class RegisterBlocks {

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for (Block block : Main.blocks) {
			event.getRegistry().register(block);
			if (block instanceof ITileEntityProvider) {
				Class<? extends TileEntity> clazz = ((ITileEntityProvider) block).createNewTileEntity(null, 0)
						.getClass();
				GameRegistry.registerTileEntity(clazz,
						new ResourceLocation(Mechanics.MODID + ":tile_" + block.getUnlocalizedName()));
			}
		}
	}

	@SubscribeEvent
	public static void registerItemsBlocks(RegistryEvent.Register<Item> event) {
		for (Block block : Main.blocks) {
			ItemBlock itemblock = new ItemBlock(block);
			MUtil.setReg(itemblock, block.getRegistryName().getResourcePath());
			event.getRegistry().register(itemblock);
		}
	}
}
