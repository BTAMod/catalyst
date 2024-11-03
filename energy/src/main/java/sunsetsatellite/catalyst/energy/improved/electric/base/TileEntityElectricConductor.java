package sunsetsatellite.catalyst.energy.improved.electric.base;

import net.minecraft.core.block.entity.TileEntity;
import sunsetsatellite.catalyst.core.util.*;
import sunsetsatellite.catalyst.core.util.mixin.interfaces.ITileEntityInit;
import sunsetsatellite.catalyst.core.util.network.Network;
import sunsetsatellite.catalyst.core.util.network.NetworkType;
import sunsetsatellite.catalyst.energy.improved.electric.api.IElectric;
import sunsetsatellite.catalyst.energy.improved.electric.api.WireProperties;

public abstract class TileEntityElectricConductor extends TileEntity implements IConduitTile, ITileEntityInit {
	public Network energyNet;
	protected WireProperties properties;
	protected long voltageRating = 0;
	protected long ampRating = 0;

	protected AveragingCounter averageAmpLoad = new AveragingCounter();
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

	public void incrementAmperage(long amps){
		averageAmpLoad.increment(worldObj,amps);
		int dif = (int) (averageAmpLoad.getLast(worldObj) - getAmpRating());
		if (dif > 0) {
			//TODO: burn cable here later
		}
	}

	public double getAverageAmpLoad(){
		return averageAmpLoad.getAverage(worldObj);
	}

	public WireProperties getProperties() {
		return properties;
	}

	@Override
	public void tick() {
		super.tick();
	}
}
