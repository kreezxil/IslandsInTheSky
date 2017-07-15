package com.darva.islandsinthesky.generation;

import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * Created by James on 9/6/2015.
 */
public class VoidWorld extends WorldProvider {
    private BlockPos spawnCoords= new BlockPos(2,65,2);
    @Override
    public String getDimensionName() {
        return "IslandsInTheSky";
    }

    @Override
    public String getInternalNameSuffix() {
        return null;
    }

    @Override
    public boolean canCoordinateBeSpawn(int x, int z) {
        return true;
    }

    @Override
    public BlockPos getRandomizedSpawnPoint() {
        return spawnCoords;
    }


    @Override
    public IChunkProvider createChunkGenerator() {
        return new VoidChunk(worldObj, worldObj.getSeed(), false, "", worldObj);
    }

    @Override
    public BlockPos getSpawnCoordinate() {
        return spawnCoords;
    }

    @Override
    public BlockPos getSpawnPoint() {
        return spawnCoords;
    }
}
