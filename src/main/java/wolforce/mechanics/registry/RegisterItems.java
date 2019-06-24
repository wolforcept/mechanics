package wolforce.mechanics.registry;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolforce.mechanics.Mechanics;

@Mod.EventBusSubscriber(modid = Mechanics.MODID)
public class RegisterItems {

	@SubscribeEvent
	public static void registerItemsBlocks(RegistryEvent.Register<Item> event) {
		// for (Item item : Main.items) {
		// ItemBlock itemblock = new ItemBlock(block);
		// Util.setReg(itemblock, block.getRegistryName().getResourcePath());
		// event.getRegistry().register(itemblock);
		// }
	}
}
