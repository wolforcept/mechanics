package wolforce.mechanics.container;

import net.minecraft.inventory.IInventory;
import wolforce.mechanics.blocks.tiles.TileAlloyFurnace;
import wolforce.mechanics.blocks.tiles.TileBase;

public class ContainerAlloyFurnace extends ContainerBase {

    public ContainerAlloyFurnace(IInventory playerInv, TileBase tile) {
        super(playerInv, tile, 4);
    }

    @Override
    void addOwnSlots() {
        addSlotArray(0, 46, 17, 1, 2, ((TileAlloyFurnace) tile).input);
        addSlotArray(2, 56, 53, 1, 1, ((TileAlloyFurnace) tile).input);
        addSlotArray(0, 116, 35, 1, 1, ((TileAlloyFurnace) tile).output);
    }
}
