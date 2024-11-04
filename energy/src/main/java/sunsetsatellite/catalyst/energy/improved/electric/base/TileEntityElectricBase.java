package sunsetsatellite.catalyst.energy.improved.electric.base;

import com.mojang.nbt.CompoundTag;
import sunsetsatellite.catalyst.core.util.AveragingCounter;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.Vec3i;
import sunsetsatellite.catalyst.core.util.mixin.interfaces.ITileEntityInit;
import sunsetsatellite.catalyst.core.util.network.Network;
import sunsetsatellite.catalyst.core.util.network.NetworkComponentTile;
import sunsetsatellite.catalyst.core.util.network.NetworkType;
import sunsetsatellite.catalyst.core.util.tile.ExtendableTileEntity;
import sunsetsatellite.catalyst.energy.improved.electric.api.IElectric;
import sunsetsatellite.catalyst.energy.improved.electric.api.IVoltageTiered;
import sunsetsatellite.catalyst.energy.improved.electric.api.VoltageTier;


@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public abstract class TileEntityElectricBase extends ExtendableTileEntity implements IElectric, IVoltageTiered, ITileEntityInit, NetworkComponentTile {

	protected long energy = 0;
	protected long capacity = 0;

	protected long maxVoltageIn = 0;
	protected long maxAmpsIn = 0;

	protected long maxVoltageOut = 0;
	protected long maxAmpsOut = 0;

	protected AveragingCounter averageAmpLoad = new AveragingCounter();
	protected AveragingCounter averageEnergyTransfer = new AveragingCounter();
	protected long ampsUsing = 0;

	public TileEntityElectricBase() {}

	@Override
	public VoltageTier getTier() {
		IVoltageTiered block = (IVoltageTiered) getBlockType();
		return block.getTier();
	}

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
		averageEnergyTransfer.increment(worldObj,difference);
		energy += difference;
		return difference;
	}

	@Override
	public double getAverageEnergyTransfer() {
		return averageEnergyTransfer.getAverage(worldObj);
	}

	@Override
	public long getAmpsCurrentlyUsed() {
		return ampsUsing;
	}

	@Override
	public void addAmpsToUse(long amperage) {
		averageAmpLoad.increment(worldObj,amperage);
		ampsUsing += amperage;
	}

	@Override
	public double getAverageAmpLoad() {
		return averageAmpLoad.getAverage(worldObj);
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

	@Override
	public void readFromNBT(CompoundTag tag) {
		energy = tag.getLong("Energy");
		super.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		tag.putLong("Energy",energy);
		super.writeToNBT(tag);
	}
}
