package rtg.world.biome.realistic.vanilla;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import rtg.config.ConfigRTG;
import rtg.world.biome.BiomeBase;
import rtg.world.biome.BiomeGenManager;
import rtg.world.biome.BiomeBase.BiomeCategory;
import rtg.world.gen.surface.vanilla.SurfaceVanillaPlains;
import rtg.world.gen.terrain.vanilla.TerrainVanillaPlains;

public class RealisticBiomeVanillaPlains extends RealisticBiomeVanillaBase
{	
	public static Block topBlock = BiomeGenBase.plains.topBlock;
	public static Block fillerBlock = BiomeGenBase.plains.fillerBlock;
	
	public RealisticBiomeVanillaPlains()
	{
		super(
			BiomeGenBase.plains,
			BiomeBase.climatizedBiome(BiomeGenBase.river, BiomeBase.Climate.TEMPERATE),
			new TerrainVanillaPlains(),
			new SurfaceVanillaPlains(topBlock, fillerBlock, Blocks.stone, Blocks.cobblestone)
		);
		
		this.setRealisticBiomeName("Vanilla Plains");
		this.biomeCategory = BiomeCategory.WET;
		BiomeGenManager.addWarmBiome(this, ConfigRTG.weightVanillaPlains);
	}
}