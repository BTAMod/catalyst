package sunsetsatellite.catalyst.core.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.catalyst.core.util.mixin.interfaces.ITileEntityInit;

@Mixin(value = Chunk.class,remap = false)
public abstract class ChunkMixin {

	@Shadow
	public abstract int getBlockID(int x, int y, int z);

	@Inject(method = "setTileEntity",at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.AFTER))
	public void setTileEntity(int x, int y, int z, TileEntity tileEntity, CallbackInfo ci){
		if(!((ITileEntityInit) tileEntity).isInitialized()){
			((ITileEntityInit) tileEntity).setInitialized();
			((ITileEntityInit) tileEntity).init(Block.getBlock(getBlockID(x,y,z)));
		}
	}
}
