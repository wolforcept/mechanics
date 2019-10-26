package wolforce.mechanics.jei.alloy;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import wolforce.mechanics.recipes.RecipeAlloyFurnace;

import java.util.List;

public class AlloyRecipeWrapper implements IRecipeWrapper {

    private RecipeAlloyFurnace recipe;

    public AlloyRecipeWrapper(RecipeAlloyFurnace recipe) {
        this.recipe = recipe;
    }


    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> input1Stacks = Lists.newArrayList(recipe.getInput1().getMatchingStacks());
        List<ItemStack> input2Stacks = Lists.newArrayList(recipe.getInput2().getMatchingStacks());
        List<List<ItemStack>> inputs = Lists.newArrayList(input1Stacks, input2Stacks);

        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutputStack());
    }
}