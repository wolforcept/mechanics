package wolforce.mechanics;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wolforce.mechanics.blocks.BlockAlloyFurnace;
import wolforce.mechanics.blocks.BlockDryingTable;

import java.util.LinkedList;

public class Main {

    public static LinkedList<Block> blocks;

    public static BlockDryingTable drying_table;
    public static BlockAlloyFurnace alloy_furnace;

    public static CreativeTabs creativeTab;

    //

    public static void preInit(FMLPreInitializationEvent event) {

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