package sunsetsatellite.catalyst.core.util.tile;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.Tag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import sunsetsatellite.catalyst.core.util.IHasFeatures;
import sunsetsatellite.catalyst.core.util.mixin.interfaces.ITileEntityInit;

import java.util.HashMap;
import java.util.Map;

public class ExtendableTileEntity extends TileEntity implements ITileEntityInit, IHasFeatures {

	protected Map<String,TEFeature> features = new HashMap<>();
	private CompoundTag loadData;

	@MustBeInvokedByOverriders
	@Override
	public void init(Block block) {
		loadFeatures();
		features.forEach((S,F)->F.init(block));
	}

	@MustBeInvokedByOverriders
	@Override
	public void tick() {
		super.tick();
		features.forEach((S,F)->F.tick());
	}

	@MustBeInvokedByOverriders
	@Override
	public void writeToNBT(CompoundTag nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		CompoundTag featuresTag = new CompoundTag();

		features.forEach((S,F)->{
			CompoundTag featureTag = new CompoundTag();
			F.writeToNBT(featureTag);
			featuresTag.putCompound(S,featureTag);
		});
		nbttagcompound.put("Features",featuresTag);
	}

	public void loadFeatures(){
		if(loadData != null){
			CompoundTag featuresTag = loadData.getCompound("Features");
			for (Tag<?> tag : featuresTag.getValues()) {
				if(tag instanceof CompoundTag) {
					CompoundTag featureTag = (CompoundTag) tag;
					TEFeature feature = TEFeature.loadFeature(featureTag, worldObj);
					if(feature != null) {
						features.put(feature.id,feature);
					}
				}
			}
			loadData = null;
		}
	}

	@MustBeInvokedByOverriders
	@Override
	public void readFromNBT(CompoundTag nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		loadData = nbttagcompound;
	}

	@Override
	public boolean hasFeature(String id){
		return features.get(id) != null;
	}

	@Override
	public TEFeature getFeature(String id){
		return features.get(id);
	}

	@Override
	public TEFeature createAndAddFeature(String featureId){
		TEFeature feature = TEFeature.createFeature(featureId, worldObj, x, y, z);
		features.put(featureId,feature);
		return feature;
	}
}
