package sunsetsatellite.catalyst.fluids.impl;


import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import sunsetsatellite.catalyst.fluids.api.IFluidInventory;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.catalyst.fluids.util.NBTHelper;

import java.util.ArrayList;

public class ItemInventoryFluid implements IInventory, IFluidInventory {
    public ItemStack[] contents = new ItemStack[2];

    public FluidStack[] fluidContents = new FluidStack[1];
    public int[] fluidCapacity = new int[1];
    public ArrayList<ArrayList<BlockFluid>> acceptedFluids = new ArrayList<>(fluidContents.length);

    public ItemStack item;

    public ItemInventoryFluid(ItemStack item) {
        this.item = item;
        for (FluidStack ignored : fluidContents) {
            acceptedFluids.add(new ArrayList<>());
        }
    }

    public ItemStack getStackInSlot(int i) {
        return this.contents[i];
    }

    public ItemStack decrStackSize(int i, int j) {
        if (this.contents[i] != null) {
            ItemStack itemstack1;
            if (this.contents[i].stackSize <= j) {
                itemstack1 = this.contents[i];
                this.contents[i] = null;
                this.onInventoryChanged();
                return itemstack1;
            } else {
                itemstack1 = this.contents[i].splitStack(j);
                if (this.contents[i].stackSize == 0) {
                    this.contents[i] = null;
                }

                this.onInventoryChanged();
                return itemstack1;
            }
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.contents[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    public int getSizeInventory() {
        return contents.length;
    }

    public String getInvName() {
        return "Item Fluid Inventory";
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public void onInventoryChanged() {
		NBTHelper.saveInvToNBT(item,this);
    }

    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

	@Override
	public void sortInventory() {

	}

	@Override
    public FluidStack getFluidInSlot(int slot) {
        if(this.fluidContents[slot] == null || this.fluidContents[slot].getLiquid() == null || this.fluidContents[slot].amount == 0){
            this.fluidContents[slot] = null;
        }
        return fluidContents[slot];
    }

    @Override
    public int getFluidCapacityForSlot(int slot) {
        return fluidCapacity[slot];
    }

    @Override
    public ArrayList<BlockFluid> getAllowedFluidsForSlot(int slot) {
        return acceptedFluids.get(slot);
    }

    public int getFluidAmountForSlot(int slot){
        if(fluidContents[0] == null){
            return 0;
        } else {
            return fluidContents[0].amount;
        }
    }

    @Override
    public void setFluidInSlot(int slot, FluidStack fluid) {
        if(fluid == null || fluid.amount == 0 || fluid.liquid == null){
            this.fluidContents[slot] = null;
            this.onFluidInventoryChanged();
            return;
        }
        if(acceptedFluids.get(slot).contains(fluid.liquid) || acceptedFluids.get(slot).isEmpty()){
            this.fluidContents[slot] = fluid;
            this.onFluidInventoryChanged();
        }

    }

    @Override
    public int getFluidInventorySize() {
        return fluidContents.length;
    }

    @Override
    public void onFluidInventoryChanged() {
		NBTHelper.saveInvToNBT(item,this);
    }

    @Override
    public int getTransferSpeed() {
        return Integer.MAX_VALUE;
    }

    @Override
    public FluidStack insertFluid(int slot, FluidStack fluidStack) {
        FluidStack stack = fluidContents[slot];
        FluidStack split = fluidStack.splitStack(Math.min(fluidStack.amount,getRemainingCapacity(slot)));
        if(stack != null){
            fluidContents[slot].amount += split.amount;
        } else {
            fluidContents[slot] = split;
        }
        return fluidStack;
    }

    @Override
    public int getRemainingCapacity(int slot) {
        if(fluidContents[slot] == null){
            return fluidCapacity[slot];
        }
        return fluidCapacity[slot]-fluidContents[slot].amount;
    }

    @Override
    public boolean canInsertFluid(int slot,FluidStack fluidStack){
        return Math.min(fluidStack.amount,getRemainingCapacity(slot)) > 0;
    }


}
