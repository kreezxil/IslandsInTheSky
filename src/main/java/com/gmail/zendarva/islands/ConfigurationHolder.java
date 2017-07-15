package com.gmail.zendarva.islands;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import java.util.LinkedList;
import java.util.List;




public class ConfigurationHolder
{
  Configuration config;
  public static int islandSize;
  public static int islandPadding;
  public static boolean voidNether;
  public static boolean protectIslands;
  private String startingInventoryString;
  LinkedList<ItemStack> startingInventory;
  static ConfigurationHolder instance;

  public static ConfigurationHolder getInstance()
  {
    if (instance == null) {
      instance = new ConfigurationHolder();
    }
    return instance;
  }

  public void LoadConfigs() {
    islandSize = this.config.get("general", "islandSize", 320).getInt();
    islandPadding = this.config.get("general", "islandPadding", 32).getInt();
    this.startingInventoryString = this.config.get("general", "startingInventory", "minecraft:dye:64:15,minecraft:sapling:4").getString();
    voidNether = this.config.get("general", "voidNether", false).getBoolean();
    protectIslands = this.config.get("general", "protectIslands", false).getBoolean();
    parseInventoryString(this.startingInventoryString);
  }

  public void save() {
    this.config.save();
  }

  public void setupConfigs(Configuration con) {
    this.config = con;
  }

  private void parseInventoryString(String inv) {
    this.startingInventory = new LinkedList();
    for (String item : inv.split(",")) {
      String[] datum = item.split(":");
      int count; int meta; if (datum.length < 4) {
        meta = 0;
        count = Integer.parseInt(datum[2]);
      } else {
        count = Integer.parseInt(datum[2]);
        meta = Integer.parseInt(datum[3]);
      }

      Item targ = Item.getByNameOrId(datum[0] + ":" + datum[1]);
      ItemStack stack = new ItemStack(targ, count, meta);
      this.startingInventory.add(stack);
    }
  }

  public List<ItemStack> getStartingInventory() {
    return this.startingInventory;
  }
}
