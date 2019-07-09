package wolforce.mechanics;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wolforce.mechanics.blocks.BlockAlloyFurnace;
import wolforce.mechanics.blocks.BlockDryingTable;
import wolforce.mechanics.items.ItemMartlet;

public class Main {

	public static LinkedList<Item> items;
	public static LinkedList<Block> blocks;

	// BLOCKS
	public static BlockDryingTable drying_table;
	public static BlockAlloyFurnace alloy_furnace;

	// ITEMS
	public static ItemMartlet martlet;

	//

	public static CreativeTabs creativeTab;

	//

	public static void preInit(FMLPreInitializationEvent event) {

		items = new LinkedList<>();

		martlet = new ItemMartlet(1f, -3.6f, ToolMaterial.STONE);
		MUtil.setReg(martlet, "martlet_stone");
		items.add(martlet);

		martlet = new ItemMartlet(7.5f, -3.6f, ToolMaterial.IRON);
		MUtil.setReg(martlet, "martlet_iron");
		items.add(martlet);

		//

		//

		blocks = new LinkedList<>();

		drying_table = new BlockDryingTable();
		drying_table.setHardness(1.2f);
		drying_table.setResistance(1.2f);
		MUtil.setReg(drying_table, "drying_table");
		blocks.add(drying_table);

		alloy_furnace = new BlockAlloyFurnace();
		alloy_furnace.setHardness(2.0f);
		alloy_furnace.setResistance(2.0f);
		MUtil.setReg(alloy_furnace, "alloy_furnace");
		blocks.add(alloy_furnace);

		creativeTab = new CreativeTab();
		for (Block block : blocks)
			block.setCreativeTab(creativeTab);
	}
}