package sunsetsatellite.catalyst.energy.improved.simple.test.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.fx.EntityFX;
import net.minecraft.client.entity.fx.EntityFlameFX;
import sunsetsatellite.catalyst.energy.improved.simple.impl.TileEntityEnergyDevice;

public class TileEntitySimpleMachine extends TileEntityEnergyDevice {
	public TileEntitySimpleMachine() {
		capacity = 1024;
		maxReceive = 8;
	}

	@Override
	public void tick() {
		super.tick();
		if(energy >= 2){
			internalRemoveEnergy(2);
			spawnParticle(new EntityFlameFX(worldObj,x+0.5f,y,z+0.5f,0,0.1,0, EntityFlameFX.Type.ORANGE));
		}
	}

	public static void spawnParticle(EntityFX particle){
		if (Minecraft.getMinecraft(Minecraft.class) == null || Minecraft.getMinecraft(Minecraft.class).thePlayer == null || Minecraft.getMinecraft(Minecraft.class).effectRenderer == null)
			return;
		double d6 = Minecraft.getMinecraft(Minecraft.class).thePlayer.x - particle.x;
		double d7 = Minecraft.getMinecraft(Minecraft.class).thePlayer.y - particle.y;
		double d8 = Minecraft.getMinecraft(Minecraft.class).thePlayer.z - particle.z;
		double d9 = 16.0D;
		if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9)
			return;
		Minecraft.getMinecraft(Minecraft.class).effectRenderer.addEffect(particle);
	}
}
