package wolforce.mechanics.registry.integration;

import java.util.Map.Entry;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import wolforce.mechanics.api.integration.JeiCat;
import wolforce.mechanics.api.integration.MakeJeiIntegration;

@JEIPlugin
public class RegisterJei implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) {

		final IJeiHelpers helpers = reg.getJeiHelpers();
		JeiCat.helpers = helpers;

		for (MakeJeiIntegration jeiInt : MakeJeiIntegration.getJeis())
			for (JeiCat cat : jeiInt.getCats())
				reg.addRecipeCategories(cat);
	}

	@Override
	public void register(IModRegistry reg) {

		for (MakeJeiIntegration jei : MakeJeiIntegration.getJeis()) {
			for (JeiCat cat : jei.getCats()) {
				for (ItemStack catalyst : cat.getCatalysts()) {
					reg.addRecipeCatalyst(catalyst, cat.getUid());
				}
				reg.addRecipes(cat.getAllRecipes(), cat.getUid());
			}

			for (Entry<Object, String[]> entry : jei.getInfos()) {
				Object obj = entry.getKey();

				if (obj instanceof Item) {
					reg.addIngredientInfo(new ItemStack((Item) obj), VanillaTypes.ITEM, entry.getValue());

				} else if (obj instanceof Block) {
					reg.addIngredientInfo(new ItemStack((Block) obj), VanillaTypes.ITEM, entry.getValue());
				}
			}
		}
	}
}
