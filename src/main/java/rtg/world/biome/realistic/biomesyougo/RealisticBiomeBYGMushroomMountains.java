package rtg.world.biome.realistic.biomesyougo;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

import rtg.api.biome.BiomeConfig;
import rtg.util.CellNoise;
import rtg.util.OpenSimplexNoise;
import rtg.util.SimplexOctave;
import rtg.world.biome.deco.DecoBaseBiomeDecorations;
import rtg.world.gen.surface.biomesyougo.SurfaceBYGMushroomMountains;
import rtg.world.gen.terrain.TerrainBase;

public class RealisticBiomeBYGMushroomMountains extends RealisticBiomeBYGBase {

    public static Biome river = Biomes.RIVER;

    public RealisticBiomeBYGMushroomMountains(Biome biome, BiomeConfig config) {

        super(config, biome, river,
            new SurfaceBYGMushroomMountains(config, biome.topBlock, biome.fillerBlock, 0f, 1.5f, 60f, 65f, 1.5f)
        );

        this.noLakes = true;
        this.noWaterFeatures = true;

        DecoBaseBiomeDecorations decoBaseBiomeDecorations = new DecoBaseBiomeDecorations();
        this.addDeco(decoBaseBiomeDecorations);
    }

    @Override
    public TerrainBase initTerrain() {

        return new TerrainBYGMushroomMountains(230f, 100f, 68f);
    }

    public class TerrainBYGMushroomMountains extends TerrainBase
    {
        private float width;
        private float strength;
        private float terrainHeight;

        private int wavelength = 39;
        private SimplexOctave.Disk jitter = new SimplexOctave.Disk();
        private double amplitude = 12;

        public TerrainBYGMushroomMountains(float mountainWidth, float mountainStrength, float height)
        {
            width = mountainWidth;
            strength = mountainStrength;
            terrainHeight = height;

            // experimentation
            terrainHeight = 30;
            width = 120;
        }

        @Override
        public float generateNoise(OpenSimplexNoise simplex, CellNoise cell, int x, int y, float border, float river)
        {
            simplex.riverJitter().evaluateNoise((float)x / wavelength, (float)y / wavelength, jitter);
            float pX = (float)((float)x + jitter.deltax() * amplitude);
            float pY = (float)((float)y + jitter.deltay() * amplitude);

            float h = simplex.noise2(pX / 19f, pY / 19f);
            h = h*h*2f;
            float h2 = simplex.noise2(pX / 13f, pY / 13f);
            h2 = h2 * h2 * 1.3f;
            h = Math.max(h, h2);
            h += h2;
            float h3 = simplex.noise2( pX / 53f, pY /53f);
            h3= h3*h3*5f;
            h+= h3;

            float m = unsignedPower(simplex.noise2(pX / width, pY / width),1.4f) * strength * river;
            // invert y and x for complexity
            float m2 = unsignedPower(simplex.noise2(pY / (width*1.5f), pX / (width*1.5f)),1.4f) * strength * river;

            m = Math.max(m, m2);

            // intensify ruggedness at height
            h = m>10? h * m/10: h;

            m = above(m,-50f);


            return terrainHeight + h + m;


        }
    }
}