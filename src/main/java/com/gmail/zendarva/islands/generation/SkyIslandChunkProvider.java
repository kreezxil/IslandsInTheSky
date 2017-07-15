package com.gmail.zendarva.islands.generation;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorFlat;

public class SkyIslandChunkProvider
  extends ChunkGeneratorFlat
{
  private World world;

  public SkyIslandChunkProvider(World worldIn)
  {
    super(worldIn, worldIn.getSeed(), false, null);
    this.world = worldIn;
    if (this.world.getWorldType() != WorldType.FLAT)
      this.world.setSeaLevel(63);
    this.world.setSpawnPoint(new BlockPos(2, 64, 2));
  }



  public void populate(int x, int z) {}


  public Chunk generateChunk(int x, int z)
  {
    Chunk ret = new Chunk(this.world, new ChunkPrimer(), x, z);
    Biome[] biomes = this.world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
    byte[] ids = ret.getBiomeArray();

    for (int i = 0; i < ids.length; i++)
    {
      ids[i] = ((byte)Biome.getIdForBiome(biomes[i]));
    }

    ret.generateSkylightMap();
    return ret;
  }
}
