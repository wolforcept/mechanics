package wolforce.mechanics.jei.dryingtable;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import wolforce.mechanics.ct.RecipeDryingTable;

public class DryingTableRecipeWrapper implements IRecipeWrapper {

	private RecipeDryingTable recipe;

	public DryingTableRecipeWrapper(RecipeDryingTable recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, recipe.getIns());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOuts());
	}
}