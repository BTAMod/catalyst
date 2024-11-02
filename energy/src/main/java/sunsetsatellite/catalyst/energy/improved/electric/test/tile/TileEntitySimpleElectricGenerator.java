package sunsetsatellite.catalyst.energy.improved.electric.test.tile;

import net.minecraft.core.block.Block;
import sunsetsatellite.catalyst.energy.improved.electric.api.IVoltageTiered;
import sunsetsatellite.catalyst.energy.improved.electric.api.VoltageTier;
import sunsetsatellite.catalyst.energy.improved.electric.base.TileEntityElectricGenerator;

public class TileEntitySimpleElectricGenerator extends TileEntityElectricGenerator {
	@Override
	public void init(Block block) {
		VoltageTier tier = ((IVoltageTiered) block).getTier();
		maxAmpsOut = 1;
		maxAmpsIn = 0;
		maxVoltageIn = 0;
		maxVoltageOut =  tier.maxVoltage;
		capacity = tier.maxVoltage * 64L;
	}

	@Override
	public void tick() {
		internalAddEnergy(maxVoltageOut * maxAmpsOut);
		super.tick();
	}
}
