package wolforce.mechanics;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wolforce.mechanics.blocks.BlockDryingTable;

public class Main {

	public static LinkedList<Block> blocks;

	public static BlockDryingTable drying_table;

	public static CreativeTabs creativeTab;

	//

	public static void preInit(FMLPreInitializationEvent event) {

		blocks = new LinkedList<>();

		drying_table = new BlockDryingTable();
		drying_table.setHardness(1.2f);
		drying_table.setResistance(1.2f);
		MUtil.setReg(drying_table, "drying_table");
		blocks.add(drying_table);

		creativeTab = new CreativeTab();
		for (Block block : blocks)
			block.setCreativeTab(creativeTab);

	}

}