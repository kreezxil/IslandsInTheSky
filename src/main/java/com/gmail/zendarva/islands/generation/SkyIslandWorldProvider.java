package com.gmail.zendarva.islands.generation;

import com.gmail.zendarva.islands.Islands;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.gen.IChunkGenerator;


public class SkyIslandWorldProvider
  extends WorldProviderSurface
{
  public boolean canCoordinateBeSpawn(int x, int z)
  {
    return true;
  }

  public BlockPos getRandomizedSpawnPoint()
  {
    if (Islands.shouldBeVoid(this.world)) {
      BlockPos spawn = new BlockPos(2, 64, 2);
      spawn = this.world.getTopSolidOrLiquidBlock(spawn);
      return spawn;
    }
    return super.getRandomizedSpawnPoint();
  }

  @Nullable
  public BlockPos getSpawnCoordinate()
  {
    if (Islands.shouldBeVoid(this.world)) {
      BlockPos spawn = new BlockPos(2, 64, 2);
      spawn = this.world.getTopSolidOrLiquidBlock(spawn);
      return spawn; }
    return super.getSpawnCoordinate();
  }

  public BlockPos getSpawnPoint()
  {
    if (Islands.shouldBeVoid(this.world)) {
      BlockPos spawn = new BlockPos(2, 64, 2);
      spawn = this.world.getTopSolidOrLiquidBlock(spawn);
      return spawn; }
    return super.getSpawnPoint();
  }

  public IChunkGenerator createChunkGenerator()
  {
    if (Islands.shouldBeVoid(this.world))
      return new SkyIslandChunkProvider(this.world);
    return super.createChunkGenerator();
  }
}
