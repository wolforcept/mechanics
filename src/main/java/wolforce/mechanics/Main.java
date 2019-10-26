package wolforce.mechanics;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wolforce.mechanics.api.MakeRegistry;
import wolforce.mechanics.blocks.BlockAlloyFurnace;
import wolforce.mechanics.blocks.BlockDryingTable;
import wolforce.mechanics.items.ItemMartlet;

public class Main {

	public static BlockDryingTable drying_table;
	public static BlockAlloyFurnace alloy_furnace;

	// ITEMS
	public static ItemMartlet martlet_stone, martlet_iron;

	//

	public static CreativeTabs creativeTab;

	//

	public static void preInit(FMLPreInitializationEvent event) {

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
}