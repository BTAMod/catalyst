package sunsetsatellite.catalyst.energy.improved.electric.base;

import net.minecraft.core.block.entity.TileEntity;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.Vec3i;
import sunsetsatellite.catalyst.core.util.network.Network;
import sunsetsatellite.catalyst.core.util.network.NetworkComponentTile;
import sunsetsatellite.catalyst.core.util.network.NetworkType;
import sunsetsatellite.catalyst.energy.improved.electric.api.IElectric;


@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public abstract class TileEntityElectricBase extends TileEntity implements IElectric, NetworkComponentTile {

	protected long energy = 0;
	protected long capacity = 0;

	protected long maxVoltageIn = 0;
	protected long maxAmpsIn = 0;

	protected long maxVoltageOut = 0;
	protected long maxAmpsOut = 0;

	protected long ampsUsing = 0;

	public TileEntityElectricBase() {}

	//IEnergyContainer
	@Override
	public long getEnergy() {
		return energy;
	}

	@Override
	public long getCapacity() {
		return capacity;
	}

	@Override
	public long getMaxInputVoltage() {
		return maxVoltageIn;
	}

	@Override
	public long getMaxInputAmperage() {
		return maxAmpsIn;
	}

	@Override
	public long getMaxOutputVoltage() {
		return maxVoltageOut;
	}

	@Override
	public long getMaxOutputAmperage() {
		return maxAmpsOut;
	}

	@Override
	public long internalChangeEnergy(long difference) {
		energy += difference;
		return difference;
	}

	@Override
	public long getAmpsCurrentlyUsed() {
		return ampsUsing;
	}

	@Override
	public void addAmpUsage(long amperage) {
		ampsUsing += amperage;
	}

	//NetworkComponent
	public Network energyNet;

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
		return direction.getTileEntity(worldObj,this) instanceof TileEntityElectricConductor;
	}

	@Override
	public void networkChanged(Network network) {
		this.energyNet = network;
	}

	@Override
	public void removedFromNetwork(Network network) {
		this.energyNet = null;
	}
}
