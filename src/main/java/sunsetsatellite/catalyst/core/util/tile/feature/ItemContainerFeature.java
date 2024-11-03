package sunsetsatellite.catalyst.core.util.tile.feature;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.core.util.tile.TEFeature;

public class ItemContainerFeature extends TEFeature implements IInventory {

	public int size = 2;
	public ItemStack[] itemContents = new ItemStack[2];

	protected ItemContainerFeature(String id, World world, int x, int y, int z) {
		super(id, world, x, y, z);
	}

	protected ItemContainerFeature(String id, World world) {
		super(id, world);
	}

	@Override
	public void tick() {

	}

	public ItemContainerFeature setSize(int size){
		this.size = size;
		return this;
	}

	@Override
	public void init(Block block) {
		itemContents = new ItemStack[size];
	}

	@Override
	public int getSizeInventory() {
		return size;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.itemContents[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(this.itemContents[i] != null) {
			ItemStack itemStack3;
			if(this.itemContents[i].stackSize <= j) {
				itemStack3 = this.itemContents[i];
				this.itemContents[i] = null;
				this.onInventoryChanged();
				return itemStack3;
			} else {
				itemStack3 = this.itemContents[i].splitStack(j);
				if(this.itemContents[i].stackSize == 0) {
					this.itemContents[i] = null;
				}

				this.onInventoryChanged();
				return itemStack3;
			}
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		this.itemContents[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "Generic Inventory Container";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {

	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return entityPlayer.distanceToSqr((double) this.x + 0.5D, (double) this.y + 0.5D, (double) this.z + 0.5D) <= 64.0D;
	}

	@Override
	public void sortInventory() {

	}

	@Override
	public void readFromNBT(CompoundTag tag) {
		super.readFromNBT(tag);
		ListTag nBTTagList2 = tag.getList("Items");
		this.size = tag.getInteger("InvSize");
		this.itemContents = new ItemStack[this.getSizeInventory()];

		for(int i3 = 0; i3 < nBTTagList2.tagCount(); ++i3) {
			CompoundTag CompoundTag4 = (CompoundTag)nBTTagList2.tagAt(i3);
			int i5 = CompoundTag4.getByte("Slot") & 255;
			if(i5 < this.itemContents.length) {
				this.itemContents[i5] = ItemStack.readItemStackFromNbt(CompoundTag4);
			}
		}
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		super.writeToNBT(tag);
		ListTag nbtTagList = new ListTag();

		for(int i3 = 0; i3 < this.itemContents.length; ++i3) {
			if(this.itemContents[i3] != null) {
				CompoundTag CompoundTag4 = new CompoundTag();
				CompoundTag4.putByte("Slot", (byte)i3);
				this.itemContents[i3].writeToNBT(CompoundTag4);
				nbtTagList.addTag(CompoundTag4);
			}
		}
		tag.putInt("InvSize",size);
		tag.put("Items", nbtTagList);
	}
}
