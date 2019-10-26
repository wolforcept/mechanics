package wolforce.mechanics.jei.dryingtable;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.util.ResourceLocation;
import wolforce.mechanics.Util;
import wolforce.mechanics.Mechanics;
import wolforce.mechanics.jei.BaseRecipeCategory;
import wolforce.mechanics.jei.JeiIntegration;

public class DryingTableRecipeCategory extends BaseRecipeCategory<DryingTableRecipeWrapper> {

	static ResourceLocation guiTexture = new ResourceLocation(Mechanics.MODID,
			"textures/gui/container/jei_drying_table.png");

	public DryingTableRecipeCategory(IGuiHelper iGuiHelper) {
		super(iGuiHelper.createDrawable(guiTexture, 0, 0, 116, 50), "tile.mechanics.drying_table.name");
	}

	@Override
	public String getUid() {
		return JeiIntegration.DRYING_TABLE;
	}

	@Override
	public String getTitle() {
		return Util.translateToLocal("mechanics.titles.drying_table.name");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DryingTableRecipeWrapper recipeWrapper,
			IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		guiItemStacks.init(0, true, 16, 16);
		guiItemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

		guiItemStacks.init(1, false, 83, 16);
		guiItemStacks.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}
}