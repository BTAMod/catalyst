package sunsetsatellite.catalyst.energy.improved.electric.test.tile;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.CatalystEnergy;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.tile.feature.ItemContainerFeature;
import sunsetsatellite.catalyst.energy.improved.electric.base.TileEntityElectricStorage;
import sunsetsatellite.catalyst.energy.improved.electric.test.block.BlockElectric;

public class TileEntitySimpleElectricBatteryBox extends TileEntityElectricStorage implements IInventory {

	public ItemContainerFeature itemContainer;

	@Override
	public void init(Block block) {
		((ItemContainerFeature) createAndAddFeature(CatalystEnergy.ITEM_CONTAINER_FEATURE)).setSize(4);
		super.init(block);
		itemContainer = (ItemContainerFeature) getFeature(CatalystEnergy.ITEM_CONTAINER_FEATURE);
		maxAmpsIn = 8;
		maxAmpsOut = 4;
		maxVoltageIn = getTier((BlockElectric) block).maxVoltage;
		maxVoltageOut = getTier((BlockElectric) block).maxVoltage;
		capacity = 128000*4;
	}

	@Override
	public boolean canProvide(@NotNull Direction dir) {
		int meta = getMovedData();
		Direction outputDir = Direction.getDirectionFromSide(meta);
		return outputDir == dir;
	}

	@Override
	public void onOvervoltage(long voltage) {

	}

	@Override
	public boolean canReceive(@NotNull Direction dir) {
		int meta = getMovedData();
		Direction outputDir = Direction.getDirectionFromSide(meta);
		return outputDir != dir;
	}

	@Override
	public void tick() {
		super.tick();
	}


	//item container delegates
	@Override
	public int getSizeInventory() {
		return itemContainer.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return itemContainer.getStackInSlot(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return itemContainer.decrStackSize(i, j);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		itemContainer.setInventorySlotContents(i, itemStack);
	}

	@Override
	public String getInvName() {
		return itemContainer.getInvName();
	}

	@Override
	public int getInventoryStackLimit() {
		return itemContainer.getInventoryStackLimit();
	}

	@Override
	public void onInventoryChanged() {
		itemContainer.onInventoryChanged();
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return itemContainer.canInteractWith(entityPlayer);
	}

	@Override
	public void sortInventory() {
		itemContainer.sortInventory();
	}
}
