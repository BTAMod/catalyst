package sunsetsatellite.catalyst.energy.electric.example.block.state;

import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.WorldSource;
import sunsetsatellite.catalyst.CatalystEnergy;


import java.util.HashMap;

public class CableBlockState {
    public static HashMap<Direction, Boolean> getStateMap(WorldSource worldSource, int i, int j, int k, Block block) {
        HashMap<Direction, Boolean> states = new HashMap<>();
        for (Direction direction : Direction.values()) {
            boolean show = false;
            int offsetX = i + direction.getOffsetX();
            int offsetY = j + direction.getOffsetY();
            int offsetZ = k + direction.getOffsetZ();
            Block neighbouringBlock = worldSource.getBlock(offsetX, offsetY, offsetZ);
            if (neighbouringBlock != null) {
                if (block.getClass().isAssignableFrom(neighbouringBlock.getClass())) {
                    show = true;
                } else if (neighbouringBlock.hasTag(CatalystEnergy.WIRES_CONNECT)) {
                    show = true;
                }
            }
            states.put(direction, show);
        }
        return states;
    }
}
