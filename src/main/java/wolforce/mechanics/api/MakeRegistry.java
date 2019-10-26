package wolforce.mechanics.api;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import wolforce.mechanics.Util;
import wolforce.mechanics.blocks.bases.HasCustomItemBlock;

public class MakeRegistry {
	private static LinkedList<MakeRegistry> registries = new LinkedList<>();

	public static LinkedList<MakeRegistry> getRegistries() {
		return registries;
	}

	private final String modid;
	private final LinkedList<Block> blocks;
	private final LinkedList<Item> items;

	public MakeRegistry(String modid) {
		this.modid = modid;
		blocks = new LinkedList<>();
		items = new LinkedList<>();
		registries.add(this);
	}

	public void add(String regName, Object obj) {

		if (obj instanceof Item) {

			setReg((Item) obj, regName);
			items.add((Item) obj);

		} else if (obj instanceof Block) {

			Block block = (Block) obj;
			setReg(block, regName);
			blocks.add(block);

			ItemBlock itemblock = (block instanceof HasCustomItemBlock) ? //
					((HasCustomItemBlock) block).getItemBlock() : //
					new ItemBlock(block);

			add(block.getRegistryName().getResourcePath(), itemblock);

		}
	}

	private void setReg(Block block, String name) {
		block.setUnlocalizedName(modid + "." + name);
		block.setRegistryName(Util.res(name));
	}

	private void setReg(Item block, String name) {
		block.setUnlocalizedName(modid + "." + name);
		block.setRegistryName(Util.res(name));
	}

	public LinkedList<Block> getBlocks() {
		return blocks;
	}

	public LinkedList<Item> getItems() {
		return items;
	}

	public void setCreativeTab(CreativeTabs creativeTab) {
		for (Block block : blocks)
			block.setCreativeTab(creativeTab);
		for (Item item : items)
			item.setCreativeTab(creativeTab);
	}

	public void setCreativeTab(String tabName, Object obj) {
		setCreativeTab(new CreativeTabs(tabName) {
			@Override
			public ItemStack getTabIconItem() {
				return obj instanceof Item ? new ItemStack((Item) obj) : new ItemStack((Block) obj);
			}
		});
	}

}
