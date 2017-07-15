package com.darva.islandsinthesky;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldSavedData;

/**
 * Created by James on 9/25/2015.
 */
public class IslandData extends WorldSavedData {

    private int version = 1;
    private int startX;
    private int startZ;
    private int nextIndex;

    private static int PADDING = 32;
    private static int ISLAND_SIZE = 160;

    final public static String key = "IslandsInTheSky.WorldData";
    public IslandData(String value) {
        super(value);
    }

    @Override
    public void readFromNBT(NBTTagCompound p_76184_1_) {
        version = p_76184_1_.getInteger("version");
        startX = p_76184_1_.getInteger("startX");
        startZ = p_76184_1_.getInteger("startZ");
        nextIndex = p_76184_1_.getInteger("nextIndex");
    }

    @Override
    public void writeToNBT(NBTTagCompound p_76187_1_) {
        p_76187_1_.setInteger("version",version);
        p_76187_1_.setInteger("startX",startX);
        p_76187_1_.setInteger("startZ",startZ);
        p_76187_1_.setInteger("nextIndex",nextIndex);
    }

    public void init()
    {
        startX = 160 + PADDING;
        startZ = 160 + PADDING;
        nextIndex = 0;
    }
    public BlockPos getNewIslandCoords()
    {
        
        int x = nextIndex % 10;
        int z = nextIndex / 10;

        x = startX + (x*ConfigurationHolder.islandSize) + (x* ConfigurationHolder.islandPadding);
        z = startZ + (z*ConfigurationHolder.islandSize) + (z*ConfigurationHolder.islandPadding);

        x+=(ConfigurationHolder.islandSize/2);
        z+=(ConfigurationHolder.islandSize/2);
        BlockPos coords = new BlockPos(x,64,z);
        this.markDirty();
        nextIndex++;
        return coords;

    }
}
