package wolforce.mechanics.api.integration;

import java.util.LinkedList;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import wolforce.mechanics.Mechanics;
import wolforce.mechanics.Util;

public abstract class JeiCat implements IRecipeCategory<IRecipeWrapper> {

	public static IJeiHelpers helpers;

	private String modid, title, uid;
	private ResourceLocation texture;
	// private int w, h;

	private IDrawableStatic back;
	private IDrawable icon;

	private LinkedList<ItemStack> catalysts;

	public JeiCat(String modid, String title, String uid, int w, int h, Block iconBlock) {
		this(modid, title, uid, w, h, new ItemStack(iconBlock));
	}

	public JeiCat(String modid, String title, String uid, int w, int h, Item iconItem) {
		this(modid, title, uid, w, h, new ItemStack(iconItem));
	}

	public JeiCat(String modid, String title, String uid, int w, int h, ItemStack iconStack) {
		this.modid = modid;
		this.title = title;
		this.uid = uid;
		this.texture = Util.res(Mechanics.MODID, "textures/gui/" + uid + ".png");
		// this.w = w;
		// this.h = h;

		this.back = helpers.getGuiHelper().drawableBuilder(texture, 0, 0, w, h).setTextureSize(w, h).build();
		this.icon = helpers.getGuiHelper().createDrawableIngredient(iconStack);

		catalysts = new LinkedList<>();
		catalysts.add(iconStack);

	}

	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getModName() {
		return modid;
	}

	@Override
	public IDrawable getBackground() {
		return back;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	public LinkedList<ItemStack> getCatalysts() {
		return catalysts;
	}

	public void addCatalyst(Item item) {
		catalysts.add(new ItemStack(item));
	}

	public void addCatalyst(Block block) {
		catalysts.add(new ItemStack(block));
	}

	public abstract LinkedList<IRecipeWrapper> getAllRecipes();

}
