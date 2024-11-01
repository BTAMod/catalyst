package sunsetsatellite.catalyst.core.mixin;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.world.World;
import net.minecraft.core.world.save.DimensionData;
import net.minecraft.core.world.save.ISaveFormat;
import net.minecraft.core.world.save.LevelStorage;
import net.minecraft.core.world.save.SaveHandlerBase;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sunsetsatellite.catalyst.core.util.mixin.interfaces.ISaveHandlerWorld;
import sunsetsatellite.catalyst.core.util.network.NetworkManager;

import java.lang.ref.WeakReference;


@Mixin(value = SaveHandlerBase.class,remap = false)
public abstract class SaveHandlerBaseMixin implements LevelStorage, ISaveHandlerWorld {

    @Shadow @Final
    ISaveFormat saveFormat;

    @Shadow @Final
    String worldDirName;

	@Unique
	private WeakReference<World> world;

	@Override
	public World getWorld() {
		return world.get();
	}

	@Override
	public void setWorld(World world) {
		this.world = new WeakReference<>(world);
	}

	@Inject(method = "getDimensionData", at = @At("HEAD"))
    public void getDimensionData(int dimensionId, CallbackInfoReturnable<DimensionData> cir) {
        CompoundTag data = saveFormat.getDimensionDataRaw(worldDirName, dimensionId);
        if(data != null){
			NetworkManager.netsFromTag(world.get(), data.getCompound("Networks"));
        }
    }

    @Inject(method = "saveDimensionDataRaw", at = @At("HEAD"))
    public void saveDimensionDataRaw(int dimensionId, CompoundTag dimensionDataTag, CallbackInfo ci) {
		CompoundTag networksNbt = new CompoundTag();
		NetworkManager.netsToTag(world.get(), networksNbt);
		dimensionDataTag.put("Networks", networksNbt);
    }
}
