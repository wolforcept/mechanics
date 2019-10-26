package wolforce.mechanics.jei;

import java.util.stream.Collectors;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import wolforce.mechanics.Mechanics;
import wolforce.mechanics.client.GuiAlloyFurnace;
import wolforce.mechanics.jei.alloy.AlloyRecipeCategory;
import wolforce.mechanics.jei.alloy.AlloyRecipeWrapper;
import wolforce.mechanics.jei.martlet.MartletRecipeCategory;
import wolforce.mechanics.jei.martlet.MartletRecipeWrapper;
import wolforce.mechanics.recipes.RecipeAlloyFurnace;
import wolforce.mechanics.recipes.RecipeDryingTable;
import wolforce.mechanics.recipes.RecipeMartlet;

@JEIPlugin
public class JeiIntegration implements IModPlugin {

	public static IJeiHelpers jeiHelpers;
	public static ICraftingGridHelper craftingGridHelper;
	public static IRecipeRegistry recipeRegistry;

	public static final String ALLOY_FURNACE = Mechanics.MODID + ".alloy_furnace";
	public static final String MARTLET = Mechanics.MODID + ".martlet";
	public static final String DRYING_TABLE = Mechanics.MODID + ".drying_table";

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		recipeRegistry = jeiRuntime.getRecipeRegistry();
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		if (registry != null) {
			IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
			registry.addRecipeCategories(new AlloyRecipeCategory(guiHelper));
			registry.addRecipeCategories(new MartletRecipeCategory(guiHelper));
		}
	}

	@Override
	public void register(IModRegistry registry) {
		jeiHelpers = registry.getJeiHelpers();

		// ALLOY FURNACE
		// ------------------------------------------------------------------------------

		registry.handleRecipes(RecipeAlloyFurnace.class, (AlloyRecipeWrapper::new), ALLOY_FURNACE);

		registry.addRecipes(
				RecipeAlloyFurnace.recipes.stream().map(AlloyRecipeWrapper::new).collect(Collectors.toList()),
				ALLOY_FURNACE);

		registry.addRecipeCatalyst(new ItemStack(Mechanics.alloy_furnace), ALLOY_FURNACE);

		registry.addRecipeClickArea(GuiAlloyFurnace.class, 86, 50, 17, 33, ALLOY_FURNACE);

		// MARTLET
		// ------------------------------------------------------------------------------

		registry.handleRecipes(RecipeMartlet.class, (MartletRecipeWrapper::new), MARTLET);

		registry.addRecipes(RecipeMartlet.recipes.stream().map(MartletRecipeWrapper::new).collect(Collectors.toList()),
				MARTLET);

		registry.addRecipeCatalyst(new ItemStack(Mechanics.martlet_stone), MARTLET);
		registry.addRecipeCatalyst(new ItemStack(Mechanics.martlet_iron), MARTLET);

		// ------------------------------------------------------------------------------
	}
}