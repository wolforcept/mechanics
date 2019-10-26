package wolforce.mechanics.integration.ct;

import java.util.Iterator;

import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wolforce.mechanics.Util;
import wolforce.mechanics.recipes.RecipeAlloyFurnace;
import wolforce.mechanics.recipes.RecipeDryingTable;
import wolforce.mechanics.recipes.RecipeMartlet;
import wolforce.mechanics.recipes.RecipeNetherPortal;

@ZenClass("mods.mechanics")
@ZenRegister
public class CtIntegration {

	private static FluidStack is(ILiquidStack iLiquidStack) {
		return CraftTweakerMC.getLiquidStack(iLiquidStack);
	}

	private static ItemStack is(IItemStack iItemStack) {
		return CraftTweakerMC.getItemStack(iItemStack);
	}

	private static ItemStack[] is(IItemStack[] _outputs) {
		ItemStack[] outputs = new ItemStack[_outputs.length];
		for (int i = 0; i < outputs.length; i++) {
			outputs[i] = is(_outputs[i]);
		}
		return outputs;
	}

	@ZenDoc("Add a recipe for the Drying Table.")
	@ZenMethod
	public static void addDryingTableRecipe(IIngredient input, IItemStack output) {
		RecipeDryingTable.addRecipe(input, output);
	}

	@ZenDoc("Add a recipe for the Drying Table specifying the time in ticks.")
	@ZenMethod
	public static void addDryingTableRecipe(IIngredient input, IItemStack output, int time) {
		RecipeDryingTable.addRecipe(input, output, time);
	}

	// ALLOY FURNACE

	@ZenDoc("Add a recipe for the Alloy Furnace.")
	@ZenMethod
	public static void addAlloyFurnaceRecipe(IIngredient input1, IIngredient input2, IItemStack output) {
		RecipeAlloyFurnace.addRecipe(input1, input2, output);
	}

	// MARTLET

	@ZenDoc("Add a recipe for the Martlet.")
	@ZenMethod
	public static void addMartletRecipe(IIngredient input, IItemStack output) {
		RecipeMartlet.addRecipe(input, output);
	}

	@ZenDoc("Remove a recipe for the Martlet.")
	@ZenMethod
	public static void removeMartletRecipe(IIngredient input) {
		RecipeMartlet.removeRecipe(input);
	}

	@ZenDoc("Add a new transformation recipe through the nether portal. It is not possible to change the number of items this way. 1 to 1 transformations only.")
	@ZenMethod
	public static void addNetherPortalRecipe(IItemStack input, IItemStack output) {
		RecipeNetherPortal.recipes.add(new RecipeNetherPortal(is(input), is(output)));
	}

	@ZenDoc("Remove a nether portal transformation recipe.")
	@ZenMethod
	public static void removeNetherPortalRecipe(IItemStack input) {
		for (Iterator<RecipeNetherPortal> it = RecipeNetherPortal.recipes.iterator(); it.hasNext();) {
			RecipeNetherPortal recipe = (RecipeNetherPortal) it.next();
			if (Util.equalExceptAmount(recipe.input, is(input))) {
				it.remove();
				return;
			}
		}
	}

}
