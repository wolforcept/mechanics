package wolforce.mechanics.jei;

import mechanics.ct.RecipeAlloyFurnace;
import mezz.jei.api.*;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import wolforce.mechanics.Main;
import wolforce.mechanics.Mechanics;
import wolforce.mechanics.jei.alloy.AlloyRecipeCategory;
import wolforce.mechanics.jei.alloy.AlloyRecipeWrapper;

import java.util.stream.Collectors;

@JEIPlugin
public class JeiIntegration implements IModPlugin {

    public static IJeiHelpers jeiHelpers;
    public static ICraftingGridHelper craftingGridHelper;
    public static IRecipeRegistry recipeRegistry;

    public static final String ALLOY_FURNACE = Mechanics.MODID + ".alloy_furnace";


    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        recipeRegistry = jeiRuntime.getRecipeRegistry();
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        if (registry != null) {
            IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
            registry.addRecipeCategories(new AlloyRecipeCategory(guiHelper));
        }
    }

    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();

        registry.handleRecipes(RecipeAlloyFurnace.class,
                (AlloyRecipeWrapper::new),
                ALLOY_FURNACE);

        registry.addRecipes(RecipeAlloyFurnace.recipes.stream()
                .map(AlloyRecipeWrapper::new)
                .collect(Collectors.toList()), ALLOY_FURNACE);

        registry.addRecipeCatalyst(new ItemStack(Main.alloy_furnace), ALLOY_FURNACE);

        //registry.addRecipeClickArea(GuiAlloyFurnace.class, 86, 50, 17, 33, ALLOY_FURNACE);
    }
}