package wolforce.mechanics.blocks.tiles;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class WrappedItemHandler implements IItemHandlerModifiable {

    private IItemHandlerModifiable internalHandler;

    public WrappedItemHandler(IItemHandlerModifiable internalHandler) {
        this.internalHandler = internalHandler;

    }

    @Override
    public int getSlots() {
        return internalHandler.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return internalHandler.getStackInSlot(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        internalHandler.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlotLimit(int slot) {
        return internalHandler.getSlotLimit(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return internalHandler.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return internalHandler.extractItem(slot, amount, simulate);
    }
}