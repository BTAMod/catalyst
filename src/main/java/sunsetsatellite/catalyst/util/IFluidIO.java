package sunsetsatellite.catalyst.util;

public interface IFluidIO {

    int getActiveFluidSlotForSide(Direction dir);

    Connection getFluidIOForSide(Direction dir);
}
