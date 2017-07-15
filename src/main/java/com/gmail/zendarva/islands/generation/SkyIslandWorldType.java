package com.gmail.zendarva.islands.generation;

import com.gmail.zendarva.islands.Islands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;



public class SkyIslandWorldType
  extends WorldType
{
  public SkyIslandWorldType()
  {
    super("skyisland");
  }

  public BiomeProvider getBiomeProvider(World world)
  {
    if (Islands.shouldBeVoid(world))
      return new SkyIslandBiomeProvider(world);
    return super.getBiomeProvider(world);
  }

  public IChunkGenerator getChunkGenerator(World world, String generatorOptions)
  {
    if (Islands.shouldBeVoid(world))
      return new SkyIslandChunkProvider(world);
    return super.getChunkGenerator(world, generatorOptions);
  }

  public int getSpawnFuzz(WorldServer world, MinecraftServer server)
  {
    return 1;
  }
}
