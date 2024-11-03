package sunsetsatellite.catalyst.energy.improved.electric.api;

import net.minecraft.core.item.ItemStack;

public interface IElectricItem {

	/**
	 * @return Amount of energy currently available in item
	 */
	long getEnergy(ItemStack stack);
	/**
	 * @return Maximum energy capacity of the item
	 */
	long getCapacity();

	/**
	 * @return Amount of unused capacity left
	 */
	default long getCapacityRemaining(ItemStack stack) {
		return getCapacity() - getEnergy(stack);
	}

	/**
	 * @return Maximum voltage this item can handle
	 */
	long getMaxVoltage();

	/**
	 * @return Maximum amount of amps this item can use
	 */
	long getMaxInputAmperage();


	/**
	 * @return Maximum amperage this item can deliver
	 */
	long getMaxOutputAmperage();

}
