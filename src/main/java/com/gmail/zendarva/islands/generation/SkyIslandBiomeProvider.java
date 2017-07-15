package com.gmail.zendarva.islands.generation;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.storage.WorldInfo;

public class SkyIslandBiomeProvider extends BiomeProvider
{
  private final World world;

  public SkyIslandBiomeProvider(World world)
  {
    super(world.getWorldInfo());
    this.world = world;
  }



  public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random rand)
  {
    BlockPos ret = super.findBiomePosition(x, z, range, biomes, rand);
    if ((x == 0) && (z == 0) && (!this.world.getWorldInfo().isInitialized()))
    {
      if (ret == null)
      {
        ret = BlockPos.ORIGIN;
      }
      this.world.setSpawnPoint(new BlockPos(2, 64, 2));
      GenerateIsland(this.world, 0, 0);
    }

    return ret;
  }

  public static void GenerateIsland(World world, int x, int z)
  {
    BlockPos target = new BlockPos(x, 63, z);
    for (int y = 0; y < 2; y++) {
      for (int lx = 0; lx < 5; lx++) {
        for (int lz = 0; lz < 5; lz++) {
          if (y == 1) {
            world.setBlockState(target.add(lx, y, lz), Blocks.GRASS.getDefaultState());
          } else {
            world.setBlockState(target.add(lx, y, lz), Blocks.HARDENED_CLAY.getDefaultState());
          }
        }
      }
    }
  }
}
