package sunsetsatellite.catalyst.energy.improved.electric.test.tile;

import net.minecraft.core.block.Block;
import sunsetsatellite.catalyst.energy.improved.electric.base.TileEntityElectricDevice;

public class TileEntitySimpleElectricMachine extends TileEntityElectricDevice{
	@Override
	public void init(Block block) {
		maxAmpsOut = 0;
		maxAmpsIn = 1;
		maxVoltageIn = getTier().maxVoltage;
		maxVoltageOut = 0;
		capacity = getTier().maxVoltage * 64L;
	}

	@Override
	public void tick() {
		super.tick();
		internalRemoveEnergy(16);
	}
}
