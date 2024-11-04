package sunsetsatellite.catalyst.energy.electric.example.block.model;

import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.energy.electric.example.block.BlockCable;
import sunsetsatellite.catalyst.energy.electric.example.block.color.BlockColorWire;
import sunsetsatellite.catalyst.energy.electric.example.block.state.CableBlockState;

import java.util.HashMap;
import java.util.Map;

public class BlockModelCable extends BlockModelStandard<BlockCable> {

	public BlockModelCable(Block block) {
		super(block);
	}
	@Override
	public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
		if(renderBlocks.useInventoryTint)
		{
			int color = ((BlockColorWire) BlockColorDispatcher.getInstance().getDispatch(block)).getFallbackColor(block,metadata);
			float r = (float)(color >> 16 & 0xff) / 255F;
			float g = (float)(color >> 8 & 0xff) / 255F;
			float b = (float)(color & 0xff) / 255F;
			GL11.glColor4f(r * brightness, g * brightness, b * brightness, alpha);
		} else {
			GL11.glColor4f(brightness, brightness, brightness, alpha);
		}
		float yOffset = 0.5F;

		setBlockBoundsForItemRender();
		GL11.glTranslatef(-0.5F, 0.0F - yOffset, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1F, 0.0F);
		renderBottomFace(tessellator, block, 0.0D, 0.0D, 0.0D, getBlockTextureFromSideAndMetadata(Side.BOTTOM, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderTopFace(tessellator, block, 0.0D, 0.0D, 0.0D, getBlockTextureFromSideAndMetadata(Side.TOP, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1F);
		renderNorthFace(tessellator, block, 0.0D, 0.0D, 0.0D, getBlockTextureFromSideAndMetadata(Side.NORTH, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderSouthFace(tessellator, block, 0.0D, 0.0D, 0.0D, getBlockTextureFromSideAndMetadata(Side.SOUTH, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1F, 0.0F, 0.0F);
		renderWestFace(tessellator, block, 0.0D, 0.0D, 0.0D, getBlockTextureFromSideAndMetadata(Side.WEST, metadata));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderEastFace(tessellator, block, 0.0D, 0.0D, 0.0D, getBlockTextureFromSideAndMetadata(Side.EAST, metadata));
		tessellator.draw();

		if (hasOverbright()){
			brightness = 1f;
			if(renderBlocks.useInventoryTint)
			{
				int color = ((BlockColorWire) BlockColorDispatcher.getInstance().getDispatch(block)).getFallbackColor(block,metadata);
				float r = (float)(color >> 16 & 0xff) / 255F;
				float g = (float)(color >> 8 & 0xff) / 255F;
				float b = (float)(color & 0xff) / 255F;
				GL11.glColor4f(r * brightness, g * brightness, b * brightness, alpha);
			} else {
				GL11.glColor4f(brightness, brightness, brightness, alpha);
			}

			int overbrightLMC = 0;
			if (LightmapHelper.isLightmapEnabled()){
				overbrightLMC = LightmapHelper.getLightmapCoord(15, 15);
			}

			tessellator.startDrawingQuads();
			if (LightmapHelper.isLightmapEnabled()){
				tessellator.setLightmapCoord(overbrightLMC);
			}
			tessellator.setNormal(0.0F, -1F, 0.0F);
			renderBottomFace(tessellator, block, 0.0D, 0.0D, 0.0D, getBlockOverbrightTextureFromSideAndMeta(Side.BOTTOM, metadata));
			tessellator.draw();

			tessellator.startDrawingQuads();
			if (LightmapHelper.isLightmapEnabled()){
				tessellator.setLightmapCoord(overbrightLMC);
			}
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderTopFace(tessellator, block, 0.0D, 0.0D, 0.0D, getBlockOverbrightTextureFromSideAndMeta(Side.TOP, metadata));
			tessellator.draw();

			tessellator.startDrawingQuads();
			if (LightmapHelper.isLightmapEnabled()){
				tessellator.setLightmapCoord(overbrightLMC);
			}
			tessellator.setNormal(0.0F, 0.0F, -1F);
			renderNorthFace(tessellator, block, 0.0D, 0.0D, 0.0D, getBlockOverbrightTextureFromSideAndMeta(Side.NORTH, metadata));
			tessellator.draw();

			tessellator.startDrawingQuads();
			if (LightmapHelper.isLightmapEnabled()){
				tessellator.setLightmapCoord(overbrightLMC);
			}
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderSouthFace(tessellator, block, 0.0D, 0.0D, 0.0D, getBlockOverbrightTextureFromSideAndMeta(Side.SOUTH, metadata));
			tessellator.draw();

			tessellator.startDrawingQuads();
			if (LightmapHelper.isLightmapEnabled()){
				tessellator.setLightmapCoord(overbrightLMC);
			}
			tessellator.setNormal(-1F, 0.0F, 0.0F);
			renderWestFace(tessellator, block, 0.0D, 0.0D, 0.0D, getBlockOverbrightTextureFromSideAndMeta(Side.WEST, metadata));
			tessellator.draw();

			tessellator.startDrawingQuads();
			if (LightmapHelper.isLightmapEnabled()){
				tessellator.setLightmapCoord(overbrightLMC);
			}
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderEastFace(tessellator, block, 0.0D, 0.0D, 0.0D, getBlockOverbrightTextureFromSideAndMeta(Side.EAST, metadata));
			tessellator.draw();
		}

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {
		WorldSource world = renderBlocks.blockAccess;
		HashMap<Direction, Boolean> stateMap = CableBlockState.getStateMap(world, x, y, z, block);

		withTextures(getTexture(world,x,y,z));

		boolean rendered = false;
		BlockCable cable = block;
		double width = (double) cable.properties.getSize() / 16d;
		if (cable.properties.getSize() < 8 && cable.properties.getSize() > 1) {
			width += 2d / 16d;
		} else if (cable.properties.getSize() > 8) {
			width -= 2d / 16d;
		} else if (cable.properties.getSize() == 1) {
			width = 2d / 16d;
		}
		double halfWidth = (1d - width) / 2;
		block.setBlockBounds(halfWidth, halfWidth, halfWidth, halfWidth + width, halfWidth + width, halfWidth + width);
		rendered |= super.render(tessellator,x,y,z);

		for (Map.Entry<Direction, Boolean> entry : stateMap.entrySet()) {
			Direction K = entry.getKey();
			Boolean V = entry.getValue();
			if (V) {
				switch (K){
					case EAST:
					{
						block.setBlockBounds(halfWidth + width, halfWidth, halfWidth, 1.0F, halfWidth + width, halfWidth + width);
						rendered |= super.render(tessellator,x,y,z);
						break;

					}
					case UP:
					{
						block.setBlockBounds(halfWidth, halfWidth + width, halfWidth, halfWidth + width, 1.0F, halfWidth + width);
						rendered |= super.render(tessellator,x,y,z);
						break;

					}
					case SOUTH:
					{
						block.setBlockBounds(halfWidth, halfWidth, halfWidth + width, halfWidth + width, halfWidth + width, 1.0F);
						rendered |= super.render(tessellator,x,y,z);
						break;

					}
					case WEST:
					{
						block.setBlockBounds(0.0F, halfWidth, halfWidth, halfWidth, halfWidth + width, halfWidth + width);
						rendered |= super.render(tessellator,x,y,z);
						break;

					}
					case DOWN:
					{
						block.setBlockBounds(halfWidth, 0.0F, halfWidth, halfWidth + width, halfWidth, halfWidth + width);
						rendered |= super.render(tessellator,x,y,z);
						break;

					}
					case NORTH:
					{
						block.setBlockBounds(halfWidth, halfWidth, 0.0F, halfWidth + width, halfWidth + width, halfWidth);
						rendered |= super.render(tessellator,x,y,z);
						break;

					}
				}
			}
		}

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		return rendered;
	}

	public String getTexture(WorldSource world, int x, int y, int z) {
		return "catalyst-energy-electric-example:block/wire";
	}

	@Override
	public void setBlockBoundsForItemRender() {
		BlockCable cable = block;
		double width = (double) cable.properties.getSize() / 16d;
		if (cable.properties.getSize() < 8 && cable.properties.getSize() > 1) {
			width += 2d / 16d;
		} else if (cable.properties.getSize() > 8) {
			width -= 2d / 16d;
		} else if (cable.properties.getSize() == 1) {
			width = 2d / 16d;
		}
		double halfWidth = (1d - width) / 2;
		block.setBlockBounds(halfWidth, halfWidth, halfWidth, halfWidth + width, halfWidth + width, halfWidth + width);
	}
}
