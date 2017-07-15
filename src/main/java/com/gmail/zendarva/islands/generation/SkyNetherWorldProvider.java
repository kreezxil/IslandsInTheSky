package com.gmail.zendarva.islands.generation;

import com.gmail.zendarva.islands.ConfigurationHolder;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.gen.IChunkGenerator;




public class SkyNetherWorldProvider
  extends WorldProviderHell
{
  public boolean canCoordinateBeSpawn(int x, int z)
  {
    if (ConfigurationHolder.voidNether)
      return true;
    return super.canCoordinateBeSpawn(x, z);
  }

  public IChunkGenerator createChunkGenerator()
  {
    if (ConfigurationHolder.voidNether)
      return new SkyIslandChunkProvider(this.world);
    return super.createChunkGenerator();
  }
}
