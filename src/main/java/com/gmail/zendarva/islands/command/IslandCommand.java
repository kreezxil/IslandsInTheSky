 package com.gmail.zendarva.islands.command;

 import com.gmail.zendarva.islands.ConfigurationHolder;
 import com.gmail.zendarva.islands.IslandData;
 import com.gmail.zendarva.islands.Islands;
 import com.gmail.zendarva.islands.capabilities.IslandCapability;
 import com.gmail.zendarva.islands.generation.SkyIslandBiomeProvider;
 import com.gmail.zendarva.islands.handlers.IslandCapabilityProvider;
 import java.util.ArrayList;
 import java.util.List;
 import net.minecraft.command.CommandBase;
 import net.minecraft.command.CommandException;
 import net.minecraft.command.ICommandSender;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.entity.player.InventoryPlayer;
 import net.minecraft.item.ItemStack;
 import net.minecraft.server.MinecraftServer;
 import net.minecraft.util.math.BlockPos;
 import net.minecraft.util.math.Vec3i;
 import net.minecraft.util.text.TextComponentString;
 import net.minecraft.world.World;
 public class IslandCommand
   extends CommandBase
 {
   private ArrayList<String> aliases;

   public IslandCommand()
   {
     this.aliases = new ArrayList();
     this.aliases.add("is");
   }

   public boolean checkPermission(MinecraftServer server, ICommandSender sender)
   {
     return true;
   }

   public List getAliases()
   {
     return this.aliases;
   }

   public String getName()
   {
     return "island";
   }

   public String getUsage(ICommandSender p_71518_1_)
   {
     return "/island will return you to your island. Creating it if necessary.";
   }

   public void execute(MinecraftServer server, ICommandSender sender, String[] args)
     throws CommandException
   {
     if (!(sender instanceof EntityPlayer))
     {
       return;
     }
     EntityPlayer player = (EntityPlayer)sender;

     if (sender.getEntityWorld().isRemote) {
       return;
     }
    if (player.dimension != 0)
    {

      ((EntityPlayer)sender).sendMessage(new TextComponentString("You cannot do that here."));
      return;
    }
    if (isMoving(player))
    {
      player.sendMessage(new TextComponentString("You must stop moving before you teleport."));
      return;
    }
    IslandCapability islandCapability = (IslandCapability)player.getCapability(IslandCapabilityProvider.islandCap, null);
    if (islandCapability.getIslandLoc() == null)
    {
      BlockPos coords = Islands.worldData.getNewIslandCoords();


      islandCapability.setIslandLoc(coords);
      islandCapability.setOwner(true);
      islandCapability.setIslandCorner(coords.subtract(new Vec3i(ConfigurationHolder.islandSize / 2, 0, ConfigurationHolder.islandSize / 2)));


      SkyIslandBiomeProvider.GenerateIsland(player.getEntityWorld(), coords.getX() - 2, coords.getZ() - 2);
      coords = player.getEntityWorld().getTopSolidOrLiquidBlock(coords);

      player.setPositionAndUpdate(coords.getX() + 0.5F, coords.getY(), coords.getZ() + 0.5F);

      for (ItemStack stack : ConfigurationHolder.getInstance().getStartingInventory())
      {
        player.inventory.addItemStackToInventory(stack.copy());
      }
      player.sendMessage(new TextComponentString("Creating your island, and sending you there."));

      return;
    }
    player.sendMessage(new TextComponentString("Taking you back to your island."));
    BlockPos coords = islandCapability.getIslandLoc();
    coords = player.world.getTopSolidOrLiquidBlock(coords);
    player.setPositionAndUpdate(coords.getX() + 0.5F, coords.getY(), coords.getZ() + 0.5F);
  }



  public static boolean isMoving(EntityPlayer player)
  {
    if (player.isAirBorne)
    {
      return true;
    }
    if (player.motionY < -0.1D)
      return true;
    return false;
  }
}