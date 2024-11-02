package sunsetsatellite.catalyst.energy.improved.electric.base;

import net.minecraft.core.block.entity.TileEntity;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.network.NetworkComponentTile;
import sunsetsatellite.catalyst.core.util.network.NetworkPath;
import sunsetsatellite.catalyst.energy.improved.electric.api.IElectric;
import sunsetsatellite.catalyst.energy.improved.simple.test.tile.TileEntityWire;



public abstract class TileEntityElectricDevice extends TileEntityElectricBase implements NetworkComponentTile {

	@Override
	public boolean canReceive(@NotNull Direction dir) {
		return true;
	}

	@Override
	public void tick() {
		super.tick();
		ampsUsing = 0;
		for (Direction dir : Direction.values()) {
			TileEntity tile = dir.getTileEntity(worldObj,this);
			if(tile instanceof TileEntityElectricConductor) {
				TileEntityElectricConductor wire = (TileEntityElectricConductor) tile;
				receiveEnergy(dir,getMaxInputAmperage());
			}
		}
	}

	@Override
	public long receiveEnergy(@NotNull Direction dir, long amperage) {
		if(amperage > getMaxInputAmperage()){
			return 0;
		}
		long remainingCapacity = getCapacityRemaining();
		TileEntity tile = dir.getTileEntity(worldObj,this);
		if(tile instanceof TileEntityWire) {
			TileEntityWire wire = (TileEntityWire) tile;

			for (NetworkPath path : energyNet.getPathData(wire.getPosition())) {
				long pathLoss = 0;
				if(path.target == this || !(path.target instanceof IElectric)){
					continue;
				}
				IElectric dest = (IElectric) path.target;

				if(dest.canProvide(path.targetDirection)) {
					if (canReceive(dir)) {
						long voltage = dest.getMaxOutputVoltage();
						amperage = Math.min(amperage, dest.getMaxOutputAmperage());
						for (NetworkComponentTile component : path.path) {
							if(component instanceof TileEntityElectricConductor){
								pathLoss += ((TileEntityElectricConductor) component).getProperties().getMaterial().getLossPerBlock();
							}
						}
						if(pathLoss >= voltage){
							//avoid paths where all energy is lost
							continue;
						}
						long pathVoltage = voltage - pathLoss;
						boolean pathBroken = false;
						for (NetworkComponentTile pathTile : path.path) {
							if(pathTile instanceof TileEntityElectricConductor){
								TileEntityElectricConductor pathWire = (TileEntityElectricConductor) pathTile;
								if(pathWire.getVoltageRating() < voltage){
									//TODO: brew something malicious here later :tf:
									pathBroken = true;
									pathVoltage = Math.min(pathWire.getVoltageRating(), pathVoltage);
									break;
								}
							}
						}
						if(pathBroken) continue;

						if(pathVoltage > 0){
							if(pathVoltage > getMaxInputVoltage()){
								//TODO: do something bad here later :tf:
								return Math.max(amperage, getMaxInputAmperage() - ampsUsing); //short circuit amperage
							}
							if(remainingCapacity > pathVoltage){
								long willUseAmps = Math.min(remainingCapacity / pathVoltage, Math.min(amperage, getMaxInputAmperage() - ampsUsing));
								if(willUseAmps > 0){
									for (NetworkComponentTile pathTile : path.path) {
										if (pathTile instanceof TileEntityElectricConductor) {
											TileEntityElectricConductor pathWire = (TileEntityElectricConductor) pathTile;
											//TODO: increment amps for wires
										}
									}
									long willUseEnergy = pathVoltage * willUseAmps;
									if(dest.getEnergy() >= willUseEnergy){
										addAmpUsage(willUseAmps);
										dest.addAmpUsage(willUseAmps);
										internalAddEnergy(willUseEnergy);
										dest.internalRemoveEnergy(willUseEnergy);
										return willUseAmps;
									}
								}
							}
						}
					}
				}
			}
		}
		return 0;
	}
}
