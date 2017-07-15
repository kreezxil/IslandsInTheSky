package com.darva.islandsinthesky.handlers;

import com.darva.islandsinthesky.IslandsInTheSky;
import com.darva.islandsinthesky.proxies.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Created by James on 9/12/2015.
 */
public class PlayerExtender implements IExtendedEntityProperties {

    public int islandX;
    public int islandZ;
    public int islandY;
    public EntityPlayer invitedBy;

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound newCompound = new NBTTagCompound();
        newCompound.setInteger("islandX", islandX);
        newCompound.setInteger("islandZ", islandZ);
        newCompound.setInteger("islandY", islandY);

        compound.setTag(EXT_PROP_NAME, newCompound);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound comp = compound.getCompoundTag(EXT_PROP_NAME);
        if (comp == null)
            return;
        islandX = comp.getInteger("islandX");
        islandZ = comp.getInteger("islandZ");
        islandY = comp.getInteger("islandY");
    }

    @Override
    public void init(Entity entity, World world) {

    }

    public static PlayerExtender get(EntityPlayer player) {
        return (PlayerExtender) player.getExtendedProperties(EXT_PROP_NAME);
    }

    public PlayerExtender(EntityPlayer player) {
        EntityPlayer player1 = player;
    }

    public final static String EXT_PROP_NAME = "IslandsInTheSkyy";

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(PlayerExtender.EXT_PROP_NAME, new PlayerExtender(player));
    }


    private static String getSaveKey(EntityPlayer player) {
        return player.getPersistentID() + ":" + EXT_PROP_NAME;
    }

    public static void saveProxyData(EntityPlayer player) {
        PlayerExtender playerData = PlayerExtender.get(player);
        NBTTagCompound savedData = new NBTTagCompound();

        playerData.saveNBTData(savedData);
        IslandsInTheSky.proxy.storeEntityData(getSaveKey(player), savedData);
    }

    public static void loadProxyData(EntityPlayer player) {
        PlayerExtender playerData = PlayerExtender.get(player);
        NBTTagCompound savedData = IslandsInTheSky.proxy.getEntityData(getSaveKey(player));

        if (savedData != null) {
            playerData.loadNBTData(savedData);
        }
    }
    public BlockPos getPos()
    {
        return new BlockPos(islandX, islandY,islandZ);
    }

}
