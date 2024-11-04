package sunsetsatellite.catalyst.energy.electric.example.block;

import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.CatalystEnergy;
import sunsetsatellite.catalyst.core.util.ICustomDescription;
import sunsetsatellite.catalyst.core.util.network.NetworkComponent;
import sunsetsatellite.catalyst.core.util.network.NetworkType;
import sunsetsatellite.catalyst.energy.improved.electric.api.IVoltageTiered;
import sunsetsatellite.catalyst.energy.improved.electric.api.VoltageTier;
import sunsetsatellite.catalyst.energy.improved.electric.base.TileEntityElectricBase;

public abstract class BlockElectric extends BlockTileEntityRotatable implements NetworkComponent, ICustomDescription, IVoltageTiered {

	public final VoltageTier tier;

	public BlockElectric(String key, int id, VoltageTier tier) {
		super(key, id, Material.metal);
		this.tier = tier;
		withTags(CatalystEnergy.WIRES_CONNECT);
	}

	@Override
	public NetworkType getType() {
		return NetworkType.ELECTRIC;
	}

	public VoltageTier getTier() {
		return tier;
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xHit, double yHit) {
		TileEntity tile = world.getBlockTileEntity(x,y,z);
		if(tile instanceof TileEntityElectricBase){
			TileEntityElectricBase elTile = (TileEntityElectricBase) tile;
					player.sendMessage("---------------");
			player.sendMessage(String.format("T: %s",elTile.getTier().name()));
			player.sendMessage(String.format("V IN/OUT: %d/%d",elTile.getMaxInputVoltage(),elTile.getMaxOutputVoltage()));
			player.sendMessage(String.format("A IN/OUT: %d/%d",elTile.getMaxInputAmperage(),elTile.getMaxOutputAmperage()));
			player.sendMessage(String.format("A: %d",elTile.getAmpsCurrentlyUsed()));
			player.sendMessage(String.format("E: %d/%d",elTile.getEnergy(),elTile.getCapacity()));
			player.sendMessage(String.format("N: %s",elTile.energyNet));
					player.sendMessage("---------------");
		}
		return super.onBlockRightClicked(world, x, y, z, player, side, xHit, yHit);
	}
}
