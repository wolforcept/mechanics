package wolforce.mechanics.jei.alloy;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.util.ResourceLocation;
import wolforce.mechanics.MUtil;
import wolforce.mechanics.Mechanics;
import wolforce.mechanics.jei.BaseRecipeCategory;
import wolforce.mechanics.jei.JeiIntegration;

public class AlloyRecipeCategory extends BaseRecipeCategory<AlloyRecipeWrapper> {

    static int u = 20;
    static int v = 5;
    static ResourceLocation guiTexture = new ResourceLocation(Mechanics.MODID, "textures/gui/container/jei_alloy_furnace.png");

    public AlloyRecipeCategory(IGuiHelper iGuiHelper) {
        super(iGuiHelper.createDrawable(guiTexture, u, v, 125, 100), "tile.mechanics.alloy_furnace.name");
    }

    @Override
    public String getUid() {
        return JeiIntegration.ALLOY_FURNACE;
    }

    @Override
    public String getTitle() {
        return MUtil.translateToLocal("tile.mechanics.alloy_furnace.name");
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AlloyRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        int x = 42 - u;
        int y = 50 - v;
        guiItemStacks.init(0, true, x, y);
        guiItemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        guiItemStacks.init(1, true, x, y + 18);
        guiItemStacks.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(1));

        x += 68;
        y = y + 9;
        guiItemStacks.init(2, false, x, y);
        guiItemStacks.set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}