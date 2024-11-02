package sunsetsatellite.catalyst.energy.improved.electric.test.block.color;

import net.minecraft.client.render.block.color.BlockColorDefault;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.WorldSource;
import sunsetsatellite.catalyst.energy.improved.electric.test.block.BlockCable;


public class BlockColorWire extends BlockColorDefault {

	public int getFallbackColor(Block block, int i) {
		return ((BlockCable) block).getProperties().getMaterial().getColor();
	}

	@Override
	public int getWorldColor(WorldSource worldSource, int i, int j, int k) {
		if(worldSource.getBlock(i,j,k) instanceof BlockCable){
			BlockCable cable = (BlockCable) worldSource.getBlock(i,j,k);
			return cable.getProperties().getMaterial().getColor();
		}
		return 0xFFFFFF;
	}
}
