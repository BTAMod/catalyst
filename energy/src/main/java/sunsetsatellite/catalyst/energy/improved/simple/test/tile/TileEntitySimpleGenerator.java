package sunsetsatellite.catalyst.energy.improved.simple.test.tile;

import sunsetsatellite.catalyst.energy.improved.simple.impl.TileEntityEnergyGenerator;

public class TileEntitySimpleGenerator extends TileEntityEnergyGenerator {

	public TileEntitySimpleGenerator() {
		capacity = 4096;
		maxProvide = 32;
		maxReceive = 0;
	}

	@Override
	public void tick() {
		super.tick();
		internalAddEnergy(1);
	}
}
