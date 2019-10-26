package wolforce.mechanics.jei.martlet;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import wolforce.mechanics.ct.RecipeMartlet;

public class MartletRecipeWrapper implements IRecipeWrapper {

	private RecipeMartlet recipe;

	public MartletRecipeWrapper(RecipeMartlet recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, recipe.getIns());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOuts());
	}
}