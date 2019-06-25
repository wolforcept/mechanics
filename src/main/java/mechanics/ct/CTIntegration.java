package mechanics.ct;

import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mechanics")
@ZenRegister
public class CTIntegration {

	// private static FluidStack is(ILiquidStack iLiquidStack) {
	// return CraftTweakerMC.getLiquidStack(iLiquidStack);
	// }
	//
	// private static ItemStack is(IItemStack iItemStack) {
	// return CraftTweakerMC.getItemStack(iItemStack);
	// }
	//
	// private static ItemStack[] is(IItemStack[] _outputs) {
	// ItemStack[] outputs = new ItemStack[_outputs.length];
	// for (int i = 0; i < outputs.length; i++) {
	// outputs[i] = is(_outputs[i]);
	// }
	// return outputs;
	// }

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

	@ZenDoc("Add a recipe for the Alloy Furnace.")
	@ZenMethod
	public static void addAlloyFurnaceRecipe(IIngredient input1, IIngredient input2, IItemStack output){
		RecipeAlloyFurnace.addRecipe(input1,input2,output);
	}
}
