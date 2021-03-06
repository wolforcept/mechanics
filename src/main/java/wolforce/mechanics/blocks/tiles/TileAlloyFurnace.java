package wolforce.mechanics.blocks.tiles;

import mechanics.ct.RecipeAlloyFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import wolforce.mechanics.MConfig;
import wolforce.mechanics.MUtil;
import wolforce.mechanics.blocks.bases.IGuiTile;

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
                if (slot == 2 && TileEntityFurnace.getItemBurnTime(stack) > 0) {
                    return super.insertItem(slot, stack, simulate);
                }
                //I'm sure this can be simplified,
                if (slot == 0) {
                    ItemStack slot1 = this.getStackInSlot(1);
                    if (!slot1.isEmpty()) {
                        if (RecipeAlloyFurnace.findRecipe(slot1, stack) != null) {
                            return super.insertItem(slot, stack, simulate);
                        } else return stack;
                    } else if (RecipeAlloyFurnace.findRecipe(stack) != null) {
                        return super.insertItem(slot, stack, simulate);
                    }
                }
                if (slot == 1) {
                    ItemStack slot0 = this.getStackInSlot(0);
                    if (!slot0.isEmpty()) {
                        if (RecipeAlloyFurnace.findRecipe(slot0, stack) != null) {
                            return super.insertItem(slot, stack, simulate);
                        } else return stack;
                    } else if (RecipeAlloyFurnace.findRecipe(stack) != null) {
                        return super.insertItem(slot, stack, simulate);
                    }
                }
                return stack;
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

    public void dropContents(World world, BlockPos pos) {
        CombinedInvWrapper wrapper = new CombinedInvWrapper(input, output);
        for (int slot = 0; slot < wrapper.getSlots(); slot++)
            MUtil.spawnItem(world, pos, wrapper.extractItem(slot, 64, false));
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            boolean dirty = false;
            if (canProcess()) {
                process();
                dirty = true;
            }else if(progressTicks > 0){
                progressTicks--;
                dirty = true;
            }
            if (this.burnTicksRemaining > 0) {
                burnTicksRemaining--;
                dirty = true;
            }
            if (dirty) markDirtyGUI();
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
            //burnTicksRemaining--;
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