package sunsetsatellite.catalyst.energy.improved.simple.impl;

import net.minecraft.core.block.entity.TileEntity;
import sunsetsatellite.catalyst.core.util.ConduitCapability;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.IConduitTile;
import sunsetsatellite.catalyst.core.util.Vec3i;
import sunsetsatellite.catalyst.core.util.network.Network;
import sunsetsatellite.catalyst.core.util.network.NetworkType;
import sunsetsatellite.catalyst.energy.improved.simple.api.IEnergyContainer;

public abstract class TileEntityEnergyConductor extends TileEntity implements IConduitTile {
	public Network energyNet;
	protected long throughput = 0;

	@Override
	public ConduitCapability getConduitCapability() {
		return ConduitCapability.CATALYST_ENERGY;
	}

	@Override
	public NetworkType getType() {
		return NetworkType.CATALYST_ENERGY;
	}

	@Override
	public Vec3i getPosition() {
		return new Vec3i(x,y,z);
	}

	@Override
	public boolean isConnected(Direction direction) {
		return direction.getTileEntity(worldObj,this) instanceof TileEntityEnergyConductor || direction.getTileEntity(worldObj,this) instanceof IEnergyContainer;
	}

	@Override
	public void networkChanged(Network network) {
		this.energyNet = network;
	}

	@Override
	public void removedFromNetwork(Network network) {
		this.energyNet = null;
	}

	public long getMaxThroughput() {
		return throughput;
	}
}
