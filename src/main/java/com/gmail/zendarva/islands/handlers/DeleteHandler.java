package com.gmail.zendarva.islands.handlers;

import com.gmail.zendarva.islands.DeleteTarget;
import com.gmail.zendarva.islands.Islands;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.LinkedList;

/**
 * Created by James on 7/15/2017.
 */
public class DeleteHandler {

    @SubscribeEvent
    public void HandleServerTick(TickEvent.WorldTickEvent event) {
        if (event.world.isRemote)
            return;
        World world = event.world;
        if (world.provider.getDimension() != 0)
            return;

        if (event.phase == TickEvent.Phase.END) {
            if (Islands.worldData == null)
                return;
            if (Islands.worldData.toDelete == null)
                Islands.worldData.toDelete= new LinkedList<>();
            if (Islands.worldData.toDelete.isEmpty())
                return;
            DeleteTarget target = Islands.worldData.toDelete.get(0);

            for (int x = 0; x < 10000; ) {
                BlockPos pos = target.getNextIndex();
                if (pos.getDistance(0, 0, 0) == 0) {
                    Islands.worldData.toDelete.remove(0);
                    break; //We're done.
                }


                if (world.isAirBlock(pos)) {
                    x++;
                    continue;
                }
                world.removeTileEntity(pos);
                world.setBlockToAir(pos);
                x += 200;
            }
        }

    }
}




