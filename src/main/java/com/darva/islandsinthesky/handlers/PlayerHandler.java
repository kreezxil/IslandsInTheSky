package com.darva.islandsinthesky.handlers;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by James on 9/26/2015.
 */
public class PlayerHandler {

    @SubscribeEvent
    public void onConstructEntity(EntityEvent.EntityConstructing event) {
        EntityPlayer player;
        if (!(event.entity instanceof EntityPlayer))
            return;
        player = (EntityPlayer) event.entity;
        PlayerExtender.register(player);
    }
    @SubscribeEvent
    public void LivingDeathEvent(LivingDeathEvent event) {
        if (event.entity.worldObj.isRemote)
            return;
        EntityPlayer player;
        if (!(event.entity instanceof EntityPlayer))
            return;
        player = (EntityPlayer) event.entity;
        PlayerExtender.saveProxyData((EntityPlayer) event.entity);
    }
    @SubscribeEvent
    public void onEnterWorld(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityPlayer)
            PlayerExtender.loadProxyData((EntityPlayer) event.entity);
    }


}
