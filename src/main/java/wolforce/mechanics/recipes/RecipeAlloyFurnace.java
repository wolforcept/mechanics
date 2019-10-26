package wolforce.mechanics.recipes;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import wolforce.mechanics.Mechanics;

import java.util.ArrayList;
import java.util.List;

public class RecipeAlloyFurnace {

    public static List<RecipeAlloyFurnace> recipes = new ArrayList<>();

    public static void addRecipe(IIngredient input1, IIngredient input2, IItemStack output) {
        recipes.add(new RecipeAlloyFurnace(input1, input2, output));
        Mechanics.logNormal("Alloy Furnace recipe added: " + input1 + " + " + input2 + " --> " + output);

    }

    public static RecipeAlloyFurnace findRecipe(ItemStack input1, ItemStack input2) {
        for (RecipeAlloyFurnace recipe : recipes) {
            if (recipe.input1.matches(CraftTweakerMC.getIItemStack(input1))) {
                if (recipe.input2.matches(CraftTweakerMC.getIItemStack(input2))) {
                    return recipe;
                }
            } else if (recipe.input2.matches(CraftTweakerMC.getIItemStack(input1))) {
                if (recipe.input1.matches(CraftTweakerMC.getIItemStack(input2))) {
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

    public RecipeAlloyFurnace(IIngredient input1, IIngredient input2, IItemStack output) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    public ItemStack getOutputStack() {
        return (ItemStack) output.getInternal();
    }

    public Ingredient getInput1() {
        return CraftTweakerMC.getIngredient(input1);
    }

    public Ingredient getInput2() {
        return CraftTweakerMC.getIngredient(input2);
    }

    public int getInput1Count() {
        return input1.getAmount();
    }

    public int getInput2Count() {
        return input2.getAmount();
    }
}