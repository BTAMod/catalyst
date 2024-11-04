package sunsetsatellite.catalyst.energy.improved.electric.test.tile;

import net.minecraft.core.block.Block;
import sunsetsatellite.catalyst.energy.improved.electric.base.TileEntityElectricConductor;
import sunsetsatellite.catalyst.energy.improved.electric.test.block.BlockCable;

public class TileEntityCable extends TileEntityElectricConductor {

	@Override
	public void init(Block block) {
		properties = ((BlockCable) block).properties;
		voltageRating = properties.getMaterial().getMaxVoltage().maxVoltage;
		ampRating = (long) properties.getSize() * properties.getMaterial().getDefaultAmps();
	}

	@Override
	public void onOvercurrent() {

	}

	@Override
	public void onOvervoltage(long voltage) {

	}
}

