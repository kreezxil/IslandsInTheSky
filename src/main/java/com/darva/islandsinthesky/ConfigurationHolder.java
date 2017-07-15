package com.darva.islandsinthesky;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 3/5/2016.
 */
public class ConfigurationHolder {
    Configuration config;
    public static int islandSize;
    public static int islandPadding;
    private String startingInventoryString;
    LinkedList<ItemStack> startingInventory;
    static ConfigurationHolder instance;

    private ConfigurationHolder() {

    }

    public static ConfigurationHolder getInstance() {
        if (instance == null) {
            instance = new ConfigurationHolder();
        }
        return instance;
    }

    public void LoadConfigs() {
        islandSize = config.get("general", "islandSize", 320).getInt();
        islandPadding = config.get("general", "islandPadding", 32).getInt();
        startingInventoryString = config.get("general", "startingInventory", "minecraft:dye:64:15,minecraft:sapling:4").getString();
        parseInventoryString(startingInventoryString);
    }

    public void save() {
        config.save();
    }

    public void setupConfigs(Configuration con) {
        config = con;
    }

    private void parseInventoryString(String inv) {
        startingInventory = new LinkedList<ItemStack>();
        for (String item : inv.split(",")) {
            String datum[] = item.split(":");
            int meta, count;
            if (datum.length < 4) {
                meta = 0;
                count = Integer.parseInt(datum[2]);
            } else {
                count = Integer.parseInt(datum[2]);
                meta = Integer.parseInt(datum[3]);
            }

            Item targ = Item.getByNameOrId(datum[0] + ":" + datum[1]);
            ItemStack stack = new ItemStack(targ, count, meta);
            startingInventory.add(stack);
        }
    }

    public List<ItemStack> getStartingInventory() {
        return startingInventory;
    }
}
