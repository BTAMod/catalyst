package sunsetsatellite.catalyst.energy.improved.electric.api;

import sunsetsatellite.catalyst.energy.improved.electric.test.block.BlockElectric;

public interface IVoltageTiered {

	VoltageTier getTier();

	default VoltageTier getTier(BlockElectric block){
		return block.getTier();
	}
}
