package wolforce.mechanics.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import wolforce.mechanics.Util;
import wolforce.mechanics.MUtilClient;
import wolforce.mechanics.blocks.tiles.TileDryingTable;

public class TesrDryingTable extends TileEntitySpecialRenderer<TileDryingTable> {

	@Override
	public void render(TileDryingTable te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {

		// float temp = MUtilClient.getNrForDebugFromHand(te.getWorld(), x, y, z) / 10f;
		for (int i = 0; i < 4; i++) {
			ItemStack stack = te.get(i);

			if (Util.isValid(stack) && MUtilClient.canRenderTESR(te)) {

				boolean isBlock = Block.getBlockFromItem(stack.getItem()) != Blocks.AIR;

				double margin = (isBlock ? 2.1 : 1.7) / 16.0;

				double scaleX = isBlock ? 1 : .75;
				double scaleY = isBlock ? 1 : .75;
				double scaleZ = isBlock ? 1 : .75;

				int dx2d = i % 2;
				int dy2d = i / 2;

				double dx = (dx2d - 1) * .5 + .25;
				double dz = (dy2d - 1) * .5 + .25;
				double dy = margin + (isBlock ? .5 : .4);

				MUtilClient.renderItem(0, 0, te.getWorld(), stack, //
						x + dx, y + dy, z + dz, //
						90, 0, 0, //
						scaleX, scaleY, scaleZ);
			}
		}
	}
}
