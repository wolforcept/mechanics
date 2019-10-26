package wolforce.mechanics.recipes;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import wolforce.mechanics.Util;
import wolforce.mechanics.Mechanics;

public class RecipeMartlet {

	public static LinkedList<RecipeMartlet> recipes = new LinkedList<>();

	public static void initRecipes() {
		addRecipe(Blocks.STONE, 0, new ItemStack(Blocks.COBBLESTONE));
		addRecipe(Blocks.COBBLESTONE, 0, new ItemStack(Blocks.GRAVEL));
		addRecipe(Blocks.GRAVEL, 0, new ItemStack(Blocks.SAND));
	}

	private static void addRecipe(Block block, int meta, ItemStack stack) {
		IIngredient input = CraftTweakerMC.getIIngredient(new ItemStack(block, 1, meta));
		IItemStack output = CraftTweakerMC.getIItemStack(stack);
		addRecipe(input, output);
	}

	public static void addRecipe(IIngredient input, IItemStack output) {
		recipes.add(new RecipeMartlet(input, output));
		Mechanics.logNormal("Martlet recipe added: " + input + " -> " + output);
	}

	public static void removeRecipe(IIngredient input) {
		for (Iterator<RecipeMartlet> iterator = recipes.iterator(); iterator.hasNext();) {
			RecipeMartlet recipe = (RecipeMartlet) iterator.next();
			if (recipe.input.equals(input))
				iterator.remove();
			for (IItemStack stack : recipe.input.getItems()) {
				if (input.matches(stack)) {
					iterator.remove();
				}
			}
		}
	}

	public static ItemStack getResult(ItemStack input) {
		if (Util.isValid(input))
			for (RecipeMartlet recipe : recipes)
				if (recipe.input.matches(CraftTweakerMC.getIItemStack(input)))
					// for (IItemStack inputItem : recipe.input.getItems())
					// if (Util.isValid(CraftTweakerMC.getItemStack(inputItem)))
					// if (CraftTweakerMC.getIItemStack(input).matches(inputItem))
					return CraftTweakerMC.getItemStack(recipe.output);
		return null;
	}

	public static Set<Block> getInputBlocks() {
		Set<Block> blocks = new HashSet<>();
		for (RecipeMartlet recipe : recipes) {
			for (IItemStack stack : recipe.input.getItems()) {
				Block block = Block.getBlockFromItem(CraftTweakerMC.getItemStack(stack).getItem());
				if (block != null)
					blocks.add(block);
			}
		}
		return blocks;
	}

	//

	//

	private IIngredient input;
	private IItemStack output;

	public RecipeMartlet(IIngredient input, IItemStack output) {
		this.input = input;
		this.output = output;
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
