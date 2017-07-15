package com.darva.islandsinthesky.handlers;

import com.darva.islandsinthesky.IslandData;
import com.darva.islandsinthesky.IslandsInTheSky;

import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by James on 9/25/2015.
 */
public class WorldDataHandler {

    @SubscribeEvent
    public void onLoad(WorldEvent.Load event)
    {
        if (event.world.isRemote)
            return;
        if (event.world.provider.getDimensionId() != 0)
            return;



        IslandData data = (IslandData) event.world.getPerWorldStorage().loadData(IslandData.class, IslandData.key);

        if (data == null)
        {
            data = new IslandData(IslandData.key);
            event.world.getPerWorldStorage().setData(data.key, data);
            data.init();
            data.markDirty();
            IslandsInTheSky.worldData = data;
            return;
        }
        IslandsInTheSky.worldData = data;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldTypeEvent event) {


    }

}
