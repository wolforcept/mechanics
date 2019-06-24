package wolforce.mechanics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Mechanics.MODID)
@Config(modid = Mechanics.MODID, category = "mechanics_config")
public class MConfig {

	public static DryingTable drying_table = new DryingTable();
	// public static Meta meta = new Meta();

	public static class DryingTable {
		@Comment({ "Drying Table default time, in ticks (default: 1200, min: 1, max: 999999)" })
		@Config.RangeInt(min = 1, max = 999999)
		public int default_time = 1200;

		@Comment({ "Drying Table works faster in the sun (default: true)" })
		public boolean faster_in_sun = true;
	}

	// public static class Meta {
	//
	// @Comment({ "Small Vaultopic Range (default: 5, min: 1, max:25)" })
	// @Config.RangeInt(min = 1, max = 25)
	// public int range_small = 5;
	//
	// @Comment({ "Big Vaultopic Range (default: 10, min: 1, max: 25)" })
	// @Config.RangeInt(min = 1, max = 25)
	// public int range_big = 10;
	// }

	@SubscribeEvent
	public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Mechanics.MODID)) {
			ConfigManager.sync(Mechanics.MODID, Config.Type.INSTANCE);
		}
	}
}
