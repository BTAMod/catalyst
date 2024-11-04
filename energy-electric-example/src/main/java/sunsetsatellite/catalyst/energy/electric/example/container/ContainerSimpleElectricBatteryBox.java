package sunsetsatellite.catalyst.energy.electric.example.container;

import deus.guilib.interfaces.IElementFather;
import deus.guilib.user.container.AdvancedContainer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;

public class ContainerSimpleElectricBatteryBox extends AdvancedContainer {
	public ContainerSimpleElectricBatteryBox(IElementFather page, IInventory playerInventory, IInventory inventory) {
		super(page, playerInventory, inventory);
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return true;
	}
}
