package wolforce.mechanics.blocks.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import wolforce.mechanics.MConfig;
import wolforce.mechanics.blocks.bases.IGuiTile;
import wolforce.mechanics.recipes.RecipeAlloyFurnace;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileAlloyFurnace extends TileBase implements ITickable, IGuiTile {

    public ItemStackHandler input;
    public ItemStackHandler output;
    //public CombinedInvWrapper inventory;
    public int burnTicksRemaining = 0;
    public int burnTicksMax = 0;
    public int progressTicks = 0;
    public RecipeAlloyFurnace currentRecipe;

    public CombinedInvWrapper automationInventory;
    public WrappedItemHandler automationInput;
    public WrappedItemHandler automationOutput;

    public TileAlloyFurnace() {
        this.input = new ItemStackHandler(3) {
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (TileEntityFurnace.getItemBurnTime(stack) > 0) {
                    if (slot == 2) return super.insertItem(slot, stack, simulate);
                    else return stack;
                }
                if (slot <= 1) return super.insertItem(slot, stack, simulate);
                else return stack;
            }

            @Override
            protected void onContentsChanged(int slot) {
                refreshRecipe();
            }
        };

        this.output = new ItemStackHandler(1) {
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return stack;
            }
        };

        automationInput = new WrappedItemHandler(input) {
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }
        };
        automationOutput = new WrappedItemHandler(output);
        automationInventory = new CombinedInvWrapper(this.automationInput, this.automationOutput);
    }

    public ItemStack getInput1() {
        return input.getStackInSlot(0);
    }

    public ItemStack getInput2() {
        return input.getStackInSlot(1);
    }

    public ItemStack getFuel() {
        return input.getStackInSlot(2);
    }

    public ItemStack getOutput() {
        return output.getStackInSlot(0);
    }

    public void refreshRecipe() {
        if (!getInput1().isEmpty()
                && !getInput2().isEmpty()) {
            this.currentRecipe = RecipeAlloyFurnace.findRecipe(getInput1(), getInput2());
        } else currentRecipe = null;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            if (canProcess()) process();
            else if (this.burnTicksRemaining > 0) burnTicksRemaining--;
        }
    }

    public boolean canProcess() {
        return this.currentRecipe != null
                && getOutput().getCount() + currentRecipe.getOutputStack().getCount() <= currentRecipe.getOutputStack().getMaxStackSize();
    }

    public void process() {
        if (this.burnTicksRemaining == 0 && !getFuel().isEmpty()) {
            this.burnTicksRemaining = TileEntityFurnace.getItemBurnTime(getFuel());
            this.burnTicksMax = this.burnTicksRemaining;
            getFuel().shrink(1);
        }
        if (burnTicksRemaining > 0) {
            burnTicksRemaining--;
            progressTicks++;
            if (progressTicks >= MConfig.alloy_furnace.default_time) {
                progressTicks = 0;
                getInput1().shrink(currentRecipe.getInput1Count());
                getInput2().shrink(currentRecipe.getInput2Count());
                if (getOutput().isEmpty()) this.output.setStackInSlot(0, currentRecipe.getOutputStack().copy());
                else getOutput().grow(currentRecipe.getOutputStack().getCount());
                refreshRecipe();
            }
        } else {
            this.burnTicksMax = 0;
        }
        markDirtyGUI();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        input.deserializeNBT(compound.getCompoundTag("input"));
        output.deserializeNBT(compound.getCompoundTag("output"));
        this.burnTicksRemaining = compound.getInteger("burnTicksRemaining");
        this.burnTicksMax = compound.getInteger("burnTicksMax");
        this.progressTicks = compound.getInteger("progressTicks");
        refreshRecipe();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("input", input.serializeNBT());
        compound.setTag("output", output.serializeNBT());
        compound.setInteger("burnTicksRemaining", this.burnTicksRemaining);
        compound.setInteger("burnTicksMax", this.burnTicksMax);
        compound.setInteger("progressTicks", this.progressTicks);
        return super.writeToNBT(compound);
    }

    @Override
    public int getGuiWidth() {
        return 176;
    }

    @Override
    public int getGuiHeight() {
        return 166;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
        else return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) automationInventory;
        } else return super.getCapability(capability, facing);
    }
}