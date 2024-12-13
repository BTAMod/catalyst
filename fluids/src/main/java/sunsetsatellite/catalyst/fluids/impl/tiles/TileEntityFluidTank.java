package sunsetsatellite.catalyst.fluids.impl.tiles;


import sunsetsatellite.catalyst.CatalystFluids;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;

public class TileEntityFluidTank extends TileEntityFluidItemContainer {
    public TileEntityFluidTank(){
        fluidCapacity[0] = 8000;
        transferSpeed = 50;
        fluidConnections.replace(Direction.Y_POS, Connection.INPUT);
        fluidConnections.replace(Direction.Y_NEG, Connection.OUTPUT);
        acceptedFluids.get(0).addAll(CatalystFluids.CONTAINERS.getAllFluids());
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public String getInvName() {
        return "Fluid Tank";
    }
}
