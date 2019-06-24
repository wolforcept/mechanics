package wolforce.mechanics;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs {

	public CreativeTab() {
		super("Mechanics");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Main.drying_table);
	}
}