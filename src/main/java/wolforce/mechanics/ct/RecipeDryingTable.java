package wolforce.mechanics.ct;

import java.util.LinkedList;
import java.util.List;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import wolforce.mechanics.MConfig;
import wolforce.mechanics.Util;
import wolforce.mechanics.Mechanics;

public class RecipeDryingTable {

	public static LinkedList<RecipeDryingTable> recipes = new LinkedList<>();

	public static void addRecipe(IIngredient input, IItemStack output) {
		addRecipe(input, output, MConfig.drying_table.default_time);
	}

	public static void addRecipe(IIngredient input, IItemStack output, int time) {
		recipes.add(new RecipeDryingTable(input, output, time));
		Mechanics.logNormal("Drying Table recipe added: " + input + " -(" + time + ")-> " + output);
	}

	public static ItemStack getResult(ItemStack input) {
		if (Util.isValid(input))
			for (RecipeDryingTable recipe : recipes)
				if (recipe.input.matches(CraftTweakerMC.getIItemStack(input)))
					// for (IItemStack inputItem : recipe.input.getItems())
					// if (Util.isValid(CraftTweakerMC.getItemStack(inputItem)))
					// if (CraftTweakerMC.getIItemStack(input).matches(inputItem))
					return CraftTweakerMC.getItemStack(recipe.output);
		return null;
	}

	public static int getTime(ItemStack input) {
		for (RecipeDryingTable recipe : recipes)
			if (recipe.input.matches(CraftTweakerMC.getIItemStack(input)))
				return recipe.time;
		return 0;
	}

	//

	//

	private IIngredient input;
	private IItemStack output;
	private int time;

	public RecipeDryingTable(IIngredient input, IItemStack output, int time) {
		this.input = input;
		this.output = output;
		this.time = time;
	}

	public List<ItemStack> getIns() {
		IItemStack[] ins = input.getItemArray();
		LinkedList<ItemStack> ret = new LinkedList<>();
		for (int i = 0; i < ins.length; i++) {
			ret.add(CraftTweakerMC.getItemStack(ins[i]));
		}
		return ret;
	}

	public ItemStack getOuts() {
		return CraftTweakerMC.getItemStack(output);
	}
}
