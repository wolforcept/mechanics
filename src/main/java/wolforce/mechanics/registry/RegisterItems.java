package wolforce.mechanics.registry;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolforce.mechanics.Mechanics;
import wolforce.mechanics.api.MakeRegistry;

@Mod.EventBusSubscriber(modid = Mechanics.MODID)
public class RegisterItems {

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		for (MakeRegistry registry : MakeRegistry.getRegistries())
			for (Item item : registry.getItems()) {
				event.getRegistry().register(item);
			}
	}
}
