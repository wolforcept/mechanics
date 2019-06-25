package wolforce.mechanics.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import wolforce.mechanics.blocks.tiles.TileAlloyFurnace;
import wolforce.mechanics.container.ContainerAlloyFurnace;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    public static final int ALLOY_FURNACE_ID = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        if (ID == ALLOY_FURNACE_ID && tile instanceof TileAlloyFurnace) {
            return new ContainerAlloyFurnace(player.inventory, (TileAlloyFurnace) tile);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        if (ID == ALLOY_FURNACE_ID && tile instanceof TileAlloyFurnace) {
            return new GuiAlloyFurnace(player.inventory, (TileAlloyFurnace) tile);
        }
        return null;
    }
}
