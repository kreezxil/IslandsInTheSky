package com.gmail.zendarva.islands;

import com.gmail.zendarva.islands.capabilities.IslandCapability;
import net.minecraft.util.math.BlockPos;

/**
 * Created by James on 7/15/2017.
 */
public class DeleteTarget {

    BlockPos corner;
    int width;
    int depth;
    BlockPos currentIndex;

    public DeleteTarget(IslandCapability cap)
    {
        this.corner= cap.getIslandCorner();
        this.currentIndex=new BlockPos(cap.getIslandCorner().getX(),0,cap.getIslandCorner().getZ());
        this.width=ConfigurationHolder.islandSize;
        this.depth=ConfigurationHolder.islandSize;
    }

    public BlockPos getNextIndex(){
        if (currentIndex.getX() <= corner.getX() + width) {
            currentIndex = currentIndex.add(1, 0, 0);
        }
        else
        {
            if (currentIndex.getZ() <= corner.getZ() + depth){
                currentIndex=currentIndex.add(width*-1,0,1);
            }
            else {
                if (currentIndex.getY() <= 256)
                    currentIndex=currentIndex.add(width*-1,1,depth*-1);
                else
                    currentIndex = new BlockPos(0,0,0);
            }
        }
        return currentIndex;
    }
}
