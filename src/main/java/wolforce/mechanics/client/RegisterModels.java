package wolforce.mechanics.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import wolforce.mechanics.Main;
import wolforce.mechanics.Mechanics;
import wolforce.mechanics.blocks.tiles.TileDryingTable;

@Mod.EventBusSubscriber(modid = Mechanics.MODID, value = Side.CLIENT)
public class RegisterModels {

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {

		// ModelLoader.setCustomModelResourceLocation(item, 0,
		// new ModelResourceLocation(item.getRegistryName(), "inventory"));

		for (Block block : Main.blocks) {
			Item itemBlock = Item.getItemFromBlock(block);
			ModelLoader.setCustomModelResourceLocation(itemBlock, 0,
					new ModelResourceLocation(itemBlock.getRegistryName(), "inventory"));
		}

		for (Item item : Main.items) {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}

		ClientRegistry.bindTileEntitySpecialRenderer(TileDryingTable.class, new TesrDryingTable());

	}

}
