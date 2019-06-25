package wolforce.mechanics.blocks.tiles;

import mechanics.ct.RecipeAlloyFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.mechanics.MConfig;
import wolforce.mechanics.blocks.bases.IGuiTile;

import javax.annotation.Nonnull;

public class TileAlloyFurnace extends TileBase implements ITickable, IGuiTile {

    public ItemStackHandler input;
    public ItemStackHandler output;
    public int burnTicksRemaining = 0;
    public int burnTicksMax = 0;
    public int progressTicks = 0;
    private RecipeAlloyFurnace currentRecipe;

    public TileAlloyFurnace() {
        this.input = new ItemStackHandler(3) {
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (slot <= 1 || (slot == 2 && TileEntityFurnace.getItemBurnTime(stack) > 0)) {
                    return super.insertItem(slot, stack, simulate);
                } else return stack;
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
}