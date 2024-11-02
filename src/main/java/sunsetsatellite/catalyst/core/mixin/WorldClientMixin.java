package sunsetsatellite.catalyst.core.mixin;

import net.minecraft.client.world.WorldClient;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.catalyst.core.util.mixin.interfaces.IAbsoluteWorldTime;

@Mixin(value = WorldClient.class,remap = false)
public class WorldClientMixin extends World {

	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo ci){
		((IAbsoluteWorldTime) this.levelData).setAbsoluteWorldTime(((IAbsoluteWorldTime) this.levelData).getAbsoluteWorldTime() + 1L);
	}
}
