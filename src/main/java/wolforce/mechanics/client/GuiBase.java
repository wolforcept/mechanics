package wolforce.mechanics.client;


import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import wolforce.mechanics.blocks.bases.IGuiTile;

import java.awt.*;

public abstract class GuiBase<T extends IGuiTile> extends GuiContainer {

    String displayName;
    ResourceLocation textureLocation;


    public GuiBase(Container container, T tile, ResourceLocation textureLocation, String displayName) {
        super(container);
        this.xSize = tile.getGuiWidth();
        this.ySize = tile.getGuiHeight();
        this.textureLocation = textureLocation;
        this.displayName = displayName;
    }

    public int getBarScaled(int pixels, int count, int max) {
        if (count > 0 && max > 0) return count * pixels / max;
        else return 0;
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(this.textureLocation);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        if (this.displayName.length() > 0) {
            this.fontRenderer.drawString(this.displayName,
                    this.xSize / 2 - this.fontRenderer.getStringWidth(this.displayName) / 2, -10, Color.WHITE.getRGB());
        }
    }
}