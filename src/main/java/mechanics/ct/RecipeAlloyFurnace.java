package mechanics.ct;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import wolforce.mechanics.MConfig;
import wolforce.mechanics.Mechanics;

import java.util.ArrayList;
import java.util.List;

public class RecipeAlloyFurnace {

    private static List<RecipeAlloyFurnace> recipes = new ArrayList<>();

    public static void addRecipe(IIngredient input1, IIngredient input2, IItemStack output) {
        addRecipe(input1, input2, output, MConfig.alloy_furnace.default_time);
    }

    public static void addRecipe(IIngredient input1, IIngredient input2, IItemStack output, int time) {
        recipes.add(new RecipeAlloyFurnace(input1, input2, output, time));
        Mechanics.logNormal("Alloy Furnace recipe added: " + input1 + " + " + input2 + " -(" + time + ")-> " + output);
    }

    public static ItemStack getResult(ItemStack input) {
        /*if (MUtil.isValid(input))
            for (RecipeAlloyFurnace recipe : recipes)
                if (recipe.input.matches(CraftTweakerMC.getIItemStack(input)))
                    // for (IItemStack inputItem : recipe.input.getItems())
                    // if (MUtil.isValid(CraftTweakerMC.getItemStack(inputItem)))
                    // if (CraftTweakerMC.getIItemStack(input).matches(inputItem))
                    return CraftTweakerMC.getItemStack(recipe.output);

         */
        return null;
    }

    public static int getTime(ItemStack input) {
        //for (RecipeAlloyFurnace recipe : recipes)
        //    if (recipe.input.matches(CraftTweakerMC.getIItemStack(input))) return recipe.time;
        return 0;
    }

    public static RecipeAlloyFurnace findRecipe(ItemStack input1, ItemStack input2) {
        for (RecipeAlloyFurnace recipe : recipes) {
            if (recipe.input1.matches(CraftTweakerMC.getIItemStack(input1))) {
                if (recipe.input2.matches(CraftTweakerMC.getIItemStack(input2))) {
                    return recipe;
                }
            } else if (recipe.input2.matches(CraftTweakerMC.getIItemStack(input2))) {
                if (recipe.input1.matches(CraftTweakerMC.getIItemStack(input1))) {
                    return recipe;
                }
            }
        }
        return null;
    }

    //

    //

    private IIngredient input1;
    private IIngredient input2;
    private IItemStack output;
    private int time;

    public RecipeAlloyFurnace(IIngredient input1, IIngredient input2, IItemStack output, int time) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.time = time;
    }

    public ItemStack getOutputStack() {
        return (ItemStack) output.getInternal();
    }

    public int getInput1Count() {
        return input1.getAmount();
    }

    public int getInput2Count() {
        return input2.getAmount();
    }
}