package sunsetsatellite.catalyst.energy.improved.electric.base;

import net.minecraft.core.block.entity.TileEntity;
import sunsetsatellite.catalyst.core.util.ConduitCapability;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.IConduitTile;
import sunsetsatellite.catalyst.core.util.Vec3i;
import sunsetsatellite.catalyst.core.util.network.Network;
import sunsetsatellite.catalyst.core.util.network.NetworkType;
import sunsetsatellite.catalyst.energy.improved.electric.api.IElectric;
import sunsetsatellite.catalyst.energy.improved.electric.api.WireProperties;

public abstract class TileEntityElectricConductor extends TileEntity implements IConduitTile {
	public Network energyNet;
	protected WireProperties properties;
	protected long voltageRating = 0;
	protected long ampRating = 0;

	protected long ampLoad = 0;
	protected long temperature = 0;

	@Override
	public ConduitCapability getConduitCapability() {
		return ConduitCapability.ELECTRIC;
	}

	@Override
	public NetworkType getType() {
		return NetworkType.ELECTRIC;
	}

	@Override
	public Vec3i getPosition() {
		return new Vec3i(x,y,z);
	}

	@Override
	public boolean isConnected(Direction direction) {
		return direction.getTileEntity(worldObj,this) instanceof TileEntityElectricConductor || direction.getTileEntity(worldObj,this) instanceof IElectric;
	}

	@Override
	public void networkChanged(Network network) {
		this.energyNet = network;
	}

	@Override
	public void removedFromNetwork(Network network) {
		this.energyNet = null;
	}

	public long getVoltageRating() {
		return voltageRating;
	}
	public long getAmpRating() {return ampRating;}

	public long getTemperature() {
		return temperature;
	}

	public WireProperties getProperties() {
		return properties;
	}
}
