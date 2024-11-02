package sunsetsatellite.catalyst.energy.improved.simple.impl;

import net.minecraft.core.block.entity.TileEntity;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.network.NetworkComponentTile;
import sunsetsatellite.catalyst.core.util.network.NetworkPath;
import sunsetsatellite.catalyst.energy.improved.simple.api.IEnergyContainer;
import sunsetsatellite.catalyst.energy.improved.simple.test.tile.TileEntityWire;

public abstract class TileEntityEnergyDevice extends TileEntityEnergyBase implements NetworkComponentTile {

	@Override
	public boolean canReceive(@NotNull Direction dir) {
		return true;
	}

	@Override
	public void tick() {
		super.tick();
		for (Direction dir : Direction.values()) {
			receiveEnergy(dir,getMaxReceive());
		}
	}

	@Override
	public long receiveEnergy(@NotNull Direction dir, long energy) {
		long energyReceived = 0;

		TileEntity tile = dir.getTileEntity(worldObj,this);
		if(tile instanceof TileEntityWire){
			TileEntityWire wire = (TileEntityWire)tile;

			for (NetworkPath path : energyNet.getPathData(wire.getPosition())) {
				long energyReceivedFromPath = 0;
				if(path.target == this || !(path.target instanceof IEnergyContainer)){
					continue;
				}

				IEnergyContainer dest = (IEnergyContainer) path.target;

				if(dest.canProvide(path.targetDirection)){
					if(canReceive(dir)){
						long maxThroughput = Long.MAX_VALUE;
						for (NetworkComponentTile component : path.path) {
							if(component instanceof TileEntityEnergyConductor){
								maxThroughput = Math.min(maxThroughput, ((TileEntityEnergyConductor) component).throughput);
							}
						}
						energyReceivedFromPath = Catalyst.multiMin(energy, maxThroughput, getMaxReceive(), dest.getMaxProvide(), getCapacityRemaining(), dest.getEnergy());
						internalChangeEnergy(energyReceivedFromPath);
						dest.internalChangeEnergy(-energyReceivedFromPath);
					}
				}
				energyReceived += energyReceivedFromPath;
			}
		}
		return energyReceived;
	}
}
