package sunsetsatellite.catalyst.energy.electric.example.block.model;

import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Sides;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.Vec3f;

import java.util.Map;

public class BlockModelWithOverlay extends BlockModelStandard<Block> {

	public Map<Direction, IconCoordinate> overlays = Catalyst.mapOf(Direction.values(), Catalyst.arrayFill(new IconCoordinate[Direction.values().length], null));
	public IconCoordinate casing;

	public BlockModelWithOverlay(Block block, String casing) {
		super(block);
		this.casing = TextureRegistry.getTexture("catalyst-energy-electric-example:block/"+casing);
		withTextures("catalyst-energy-electric-example:block/"+casing);
	}

	public BlockModelWithOverlay changeOverlay(Direction dir, String tex){
		overlays.put(dir,TextureRegistry.getTexture("catalyst-energy-electric-example:block/"+tex));
		return this;
	}

	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {
		for (Direction dir : overlays.keySet()) {
			IconCoordinate tex = overlays.get(dir);
			if(tex == null) {
				continue;
			}
			renderBlocks.overrideBlockTexture = tex;
			renderBlocks.useInventoryTint = false;
			renderBlocks.enableAO = true;
			int data = renderBlocks.blockAccess.getBlockMetadata(x, y, z);
			int side = dir.getSideNumber();
			int index = Sides.orientationLookUpHorizontal[6 * Math.min(data, 5) + side];
			if (index >= Sides.orientationLookUpHorizontal.length) continue;

			side = index;
			Vec3f vec = Direction.getDirectionFromSide(side).getVecF().divide(100);

			renderBlocks.colorRedTopLeft = renderBlocks.colorRedBottomLeft = renderBlocks.colorRedBottomRight = renderBlocks.colorRedTopRight = 1 * RenderBlocks.SIDE_LIGHT_MULTIPLIER[side];
			renderBlocks.colorGreenTopLeft = renderBlocks.colorGreenBottomLeft = renderBlocks.colorGreenBottomRight = renderBlocks.colorGreenTopRight = 1 * RenderBlocks.SIDE_LIGHT_MULTIPLIER[side];
			renderBlocks.colorBlueTopLeft = renderBlocks.colorBlueBottomLeft = renderBlocks.colorBlueBottomRight = renderBlocks.colorBlueTopRight = 1 * RenderBlocks.SIDE_LIGHT_MULTIPLIER[side];

			if (side == 0) {
				this.renderBottomFace(tessellator, block, x + vec.x, y + vec.y, z + vec.z, tex);
			} else if (side == 1) {
				this.renderTopFace(tessellator, block, x + vec.x, y + vec.y, z + vec.z, tex);
			} else if (side == 2) {
				this.renderNorthFace(tessellator, block, x + vec.x, y + vec.y, z + vec.z, tex);
			} else if (side == 3) {
				this.renderSouthFace(tessellator, block, x + vec.x, y + vec.y, z + vec.z, tex);
			} else if (side == 4) {
				this.renderWestFace(tessellator, block, x + vec.x, y + vec.y, z + vec.z, tex);
			} else if (side == 5) {
				this.renderEastFace(tessellator, block, x + vec.x, y + vec.y, z + vec.z, tex);
			}
			renderBlocks.useInventoryTint = true;
			renderBlocks.overrideBlockTexture = null;
		}
		return super.render(tessellator, x, y, z);
	}

	@Override
	public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
		super.renderBlockOnInventory(tessellator, metadata, brightness, alpha, lightmapCoordinate);

		float yOffset = 0.5F;
		setBlockBoundsForItemRender();
		GL11.glTranslatef(-0.5F, 0.0F - yOffset, -0.5F);

		tessellator.startDrawingQuads();
		if (LightmapHelper.isLightmapEnabled() && lightmapCoordinate != null){
			tessellator.setLightmapCoord(lightmapCoordinate);
		}

		Vec3f vec = Direction.Z_NEG.getVecF();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderSouthFace(tessellator, block, 0,0,0, overlays.get(Direction.Z_NEG));
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
