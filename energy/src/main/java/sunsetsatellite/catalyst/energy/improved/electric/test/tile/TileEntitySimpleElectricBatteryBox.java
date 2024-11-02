package sunsetsatellite.catalyst.energy.improved.electric.test.tile;

import net.minecraft.core.block.Block;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.energy.improved.electric.base.TileEntityElectricDevice;

public class TileEntitySimpleElectricBatteryBox extends TileEntityElectricDevice {
	@Override
	public void init(Block block) {
		maxAmpsIn = 1;
		maxAmpsOut = 4;
		maxVoltageIn = getTier().maxVoltage;
		maxVoltageOut = getTier().maxVoltage;
		capacity = 0;
	}

	@Override
	public boolean canProvide(@NotNull Direction dir) {
		int meta = getMovedData();
		Direction outputDir = Direction.getDirectionFromSide(meta);
		return outputDir == dir;
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
}
