package wolforce.mechanics.integration.jei;

import java.util.LinkedList;

import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import wolforce.mechanics.Mechanics;
import wolforce.mechanics.api.integration.JeiCat;
import wolforce.mechanics.recipes.RecipeDryingTable;

public class JeiCatDryingTable extends JeiCat {

	public JeiCatDryingTable() {
		super(Mechanics.MODID, "Drying Table Recipes", "drying_table", 116, 50, Blocks.OBSIDIAN);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(0, true, 16, 16);
		guiItemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

		guiItemStacks.init(1, false, 83, 16);
		guiItemStacks.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> list = new LinkedList<>();
		for (RecipeDryingTable recipe : RecipeDryingTable.recipes) {
			list.add(new IRecipeWrapper() {

				@Override
				public void getIngredients(IIngredients ingredients) {
					ingredients.setInputs(VanillaTypes.ITEM, recipe.getIns());
					ingredients.setOutput(VanillaTypes.ITEM, recipe.getOuts());
				}
			});
		}
		return list;
	}
}
