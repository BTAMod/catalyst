package sunsetsatellite.catalyst.core.mixin;

import net.minecraft.core.world.save.LevelStorage;
import net.minecraft.core.world.save.SaveHandlerBase;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(value = SaveHandlerBase.class,remap = false)
public abstract class SaveHandlerBaseMixin implements LevelStorage {

    /*@Shadow @Final
    ISaveFormat saveFormat;

    @Shadow @Final
    String worldDirName;


	@Inject(method = "getDimensionData", at = @At("HEAD"))
    public void getDimensionData(int dimensionId, CallbackInfoReturnable<DimensionData> cir) {
        CompoundTag data = saveFormat.getDimensionDataRaw(worldDirName, dimensionId);
        if(data != null){
			NetworkManager.netsFromTag(null, data.getCompound("Networks"));
        }
    }

    @Inject(method = "saveDimensionDataRaw", at = @At("HEAD"))
    public void saveDimensionDataRaw(int dimensionId, CompoundTag dimensionDataTag, CallbackInfo ci) {
		CompoundTag networksNbt = new CompoundTag();
		NetworkManager.netsToTag(null, networksNbt);
		dimensionDataTag.put("Networks", networksNbt);
    }*/
}
