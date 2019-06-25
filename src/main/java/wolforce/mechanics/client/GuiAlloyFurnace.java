package wolforce.mechanics.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import wolforce.mechanics.MConfig;
import wolforce.mechanics.MUtil;
import wolforce.mechanics.Mechanics;
import wolforce.mechanics.blocks.bases.IGuiTile;
import wolforce.mechanics.blocks.tiles.TileAlloyFurnace;
import wolforce.mechanics.blocks.tiles.TileBase;
import wolforce.mechanics.container.ContainerAlloyFurnace;

public class GuiAlloyFurnace extends GuiBase {

    public final static ResourceLocation texture = new ResourceLocation(Mechanics.MODID, "textures/gui/container/alloy_furnace.png");
    public TileAlloyFurnace tile;

    public GuiAlloyFurnace(InventoryPlayer invPlayer, TileBase tile) {
        super(new ContainerAlloyFurnace(invPlayer, tile), (IGuiTile) tile, texture,
                MUtil.translateToLocal("tile.mechanics.alloy_furnace.name"));
        this.tile = (TileAlloyFurnace) tile;
    }


    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (this.tile.burnTicksRemaining > 0) {
            int k = this.getBurnLeftScaled(24);//, tile.burnTicksRemaining, tile.burnTicksMax);
            this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        // int l = this.getBarScaled(13, tile.progressTicks, MConfig.alloy_furnace.default_time);
        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
    }

    private int getCookProgressScaled(int pixels) {
        if (this.tile.currentRecipe != null) {
            int i = this.tile.progressTicks;
            int j = MConfig.alloy_furnace.default_time;
            return j != 0 && i != 0 ? i * pixels / j : 0;
        }
        return 0;
    }

    private int getBurnLeftScaled(int pixels) {
        int i = this.tile.burnTicksMax;

        if (i == 0) {
            i = 200;
        }

        return this.tile.burnTicksRemaining * pixels / i;
    }

}