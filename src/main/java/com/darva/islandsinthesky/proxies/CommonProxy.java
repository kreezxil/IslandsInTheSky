package com.darva.islandsinthesky.proxies;

import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by James on 9/26/2015.
 */
public class CommonProxy {

    private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();

    public static void storeEntityData(String id, NBTTagCompound compound) {
        extendedEntityData.put(id, compound);
    }


    public static NBTTagCompound getEntityData(String name) {
        return extendedEntityData.remove(name);
    }
}
