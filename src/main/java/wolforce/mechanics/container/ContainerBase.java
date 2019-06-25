package wolforce.mechanics.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import wolforce.mechanics.blocks.bases.IGuiTile;
import wolforce.mechanics.blocks.tiles.TileBase;

abstract class ContainerBase extends Container {

    TileBase tile;
    final int SIZE;

    public ContainerBase(IInventory playerInv, TileBase tile, int size) {
        this.tile = tile;
        this.SIZE = size;
        addOwnSlots();
        addPlayerSlots(playerInv);
    }

    abstract void addOwnSlots();

    public void addSlotArray(int startingIndex, int x, int y, int rows, int columns, IItemHandler handler) {
        int initialX = x;

        int index = startingIndex;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.addSlotToContainer(new SlotItemHandler(handler, index, x, y));
                x += 18;
                index++;
            }
            x = initialX;
            y += 18;
        }
    }


    public void addPlayerSlots(IInventory playerInventory) {
        for (int row = 0; row <= 2; row++) {
            for (int col = 0; col < 8; col++) {
                int x = 8 + col * 18;
                int y = row * 18 + ((IGuiTile) tile).getGuiHeight() - 82;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        for (int row = 0; row < 8; row++) {
            int x = 8 + row * 18;
            int y = ((IGuiTile) tile).getGuiHeight() - 24;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.canInteractWith(playerIn);
    }


    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.SIZE) {
                if (!this.mergeItemStack(itemstack1, this.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.SIZE, false)) return ItemStack.EMPTY;

            if (itemstack1.getCount() <= 0) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
        }
        return itemstack;
    }
}