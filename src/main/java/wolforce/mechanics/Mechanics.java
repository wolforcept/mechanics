package wolforce.mechanics;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import wolforce.mechanics.api.MakeRegistry;
import wolforce.mechanics.blocks.BlockAlloyFurnace;
import wolforce.mechanics.blocks.BlockDryingTable;
import wolforce.mechanics.client.GuiHandler;
import wolforce.mechanics.items.ItemMartlet;
import wolforce.mechanics.recipes.RecipeMartlet;

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

	public static BlockDryingTable drying_table;
	public static BlockAlloyFurnace alloy_furnace;

	// ITEMS
	public static ItemMartlet martlet_stone, martlet_iron;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// if (event.getSide() == Side.CLIENT)
		// RegisterModels.preInit();

		MakeRegistry reg = new MakeRegistry(Mechanics.MODID);

		martlet_stone = new ItemMartlet(1f, -3.6f, ToolMaterial.STONE);
		reg.add("martlet_stone", martlet_stone);

		martlet_iron = new ItemMartlet(7.5f, -3.6f, ToolMaterial.IRON);
		reg.add("martlet_iron", martlet_iron);

		drying_table = new BlockDryingTable();
		drying_table.setHardness(1.2f);
		drying_table.setResistance(1.2f);
		reg.add("drying_table", drying_table);

		alloy_furnace = new BlockAlloyFurnace();
		alloy_furnace.setHardness(2.0f);
		alloy_furnace.setResistance(2.0f);
		reg.add("alloy_furnace", alloy_furnace);

		reg.setCreativeTab("Mechanics", drying_table);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Mechanics.instance, new GuiHandler());
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