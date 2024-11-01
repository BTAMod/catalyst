package sunsetsatellite.catalyst.core.mixin;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.world.World;
import net.minecraft.core.world.save.LevelStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.BlockChangeInfo;
import sunsetsatellite.catalyst.core.util.Vec3i;
import sunsetsatellite.catalyst.core.util.mixin.interfaces.ISaveHandlerWorld;
import sunsetsatellite.catalyst.core.util.network.NetworkManager;

@Mixin(value = World.class,remap = false)
public abstract class WorldMixin {

	@Shadow
	@Final
	public LevelStorage saveHandler;

	@Shadow public abstract int getBlockMetadata(int x, int y, int z);

	@Shadow public abstract int getBlockId(int x, int y, int z);

	@Shadow public abstract TileEntity getBlockTileEntity(int x, int y, int z);

	@Unique
	private final World thisAs = (World)((Object)this);

	@Inject(method = "<init>(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/Dimension;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/save/LevelStorage;getDimensionData(I)Lnet/minecraft/core/world/save/DimensionData;", shift = At.Shift.BEFORE))
	public void init1(CallbackInfo ci){
		((ISaveHandlerWorld) saveHandler).setWorld(thisAs);
	}

	@Inject(method = "<init>(Lnet/minecraft/core/world/save/LevelStorage;Ljava/lang/String;Lnet/minecraft/core/world/Dimension;Lnet/minecraft/core/world/type/WorldType;J)V", at = @At(value = "TAIL"))
	public void init2(CallbackInfo ci){
		((ISaveHandlerWorld) saveHandler).setWorld(thisAs);
	}

	@Inject(method = "<init>(Lnet/minecraft/core/world/save/LevelStorage;Ljava/lang/String;JLnet/minecraft/core/world/Dimension;Lnet/minecraft/core/world/type/WorldType;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/save/LevelStorage;getDimensionData(I)Lnet/minecraft/core/world/save/DimensionData;", shift = At.Shift.BEFORE))
	public void init3(CallbackInfo ci){
		((ISaveHandlerWorld) saveHandler).setWorld(thisAs);
	}

	@Inject(method = "<init>(Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/Dimension;)V", at = @At("TAIL"))
	public void init4(CallbackInfo ci){
		NetworkManager.updateAllNets();
	}

	@Inject(method = "<init>(Lnet/minecraft/core/world/save/LevelStorage;Ljava/lang/String;Lnet/minecraft/core/world/Dimension;Lnet/minecraft/core/world/type/WorldType;J)V", at = @At(value = "TAIL"))
	public void init5(CallbackInfo ci){
		NetworkManager.updateAllNets();
	}

	@Inject(method = "<init>(Lnet/minecraft/core/world/save/LevelStorage;Ljava/lang/String;JLnet/minecraft/core/world/Dimension;Lnet/minecraft/core/world/type/WorldType;)V", at = @At("TAIL"))
	public void init6(CallbackInfo ci){
		NetworkManager.updateAllNets();
	}

	@Inject(method = "setBlock", at = @At("RETURN"))
	public void setBlock(int x, int y, int z, int id, CallbackInfoReturnable<Boolean> cir){
		Catalyst.ANY_BLOCK_CHANGED_SIGNAL.emit(new BlockChangeInfo(thisAs,new Vec3i(x,y,z),id,getBlockMetadata(x,y,z)));
		if(getBlockTileEntity(x,y,z) != null || id == 0){
			Catalyst.TILE_ENTITY_BLOCK_CHANGED_SIGNAL.emit(new BlockChangeInfo(thisAs,new Vec3i(x,y,z),id,getBlockMetadata(x,y,z)));
		}
	}

	@Inject(method = "setBlockMetadata", at = @At("RETURN"))
	public void setBlockMetadata(int x, int y, int z, int meta, CallbackInfoReturnable<Boolean> cir){
		Catalyst.ANY_BLOCK_CHANGED_SIGNAL.emit(new BlockChangeInfo(thisAs,new Vec3i(x,y,z),getBlockId(x,y,z),meta));
		if(getBlockTileEntity(x,y,z) != null){
			Catalyst.TILE_ENTITY_BLOCK_CHANGED_SIGNAL.emit(new BlockChangeInfo(thisAs,new Vec3i(x,y,z),getBlockId(x,y,z),meta));
		}
	}

	@Inject(method = "setBlockAndMetadata", at = @At("RETURN"))
	public void setBlockAndMetadata(int x, int y, int z, int id, int meta, CallbackInfoReturnable<Boolean> cir){
		Catalyst.ANY_BLOCK_CHANGED_SIGNAL.emit(new BlockChangeInfo(thisAs,new Vec3i(x,y,z),id,meta));
		if(getBlockTileEntity(x,y,z) != null || id == 0){
			Catalyst.TILE_ENTITY_BLOCK_CHANGED_SIGNAL.emit(new BlockChangeInfo(thisAs,new Vec3i(x,y,z),id,getBlockMetadata(x,y,z)));
		}
	}
}
