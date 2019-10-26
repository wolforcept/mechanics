package wolforce.mechanics;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wolforce.mechanics.client.GuiHandler;
import wolforce.mechanics.ct.RecipeMartlet;
import wolforce.mechanics.registry.RegisterModels;

@Mod(modid = Mechanics.MODID, name = Mechanics.NAME, version = Mechanics.VERSION)
@Mod.EventBusSubscriber(modid = Mechanics.MODID)
public class Mechanics {

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@Instance(Mechanics.MODID)
	public static Mechanics instance;

	public static final String MODID = "mechanics";
	public static final String NAME = "Mechanics";
	public static final String VERSION = "0.2.1";
	public static final Logger logger = LogManager.getLogger(NAME);

	public static final int VICE = 1;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Main.preInit(event);
		if (event.getSide() == Side.CLIENT)
			RegisterModels.preInit();

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Mechanics.instance, new GuiHandler());
		Main.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		RecipeMartlet.initRecipes();
	}

	public static void logNormal(String string) {
		logger.log(Level.INFO, string);
	}

	public static void logError(String string) {
		logger.log(Level.ERROR, string);
	}

}