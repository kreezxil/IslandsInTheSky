package com.darva.islandsinthesky;

import com.darva.islandsinthesky.commands.*;
import com.darva.islandsinthesky.generation.VoidWorld;
import com.darva.islandsinthesky.generation.VoidWorldType;
import com.darva.islandsinthesky.handlers.PlayerHandler;
import com.darva.islandsinthesky.handlers.WorldDataHandler;
import com.darva.islandsinthesky.proxies.CommonProxy;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by James on 9/25/2015.
 */
@Mod(name = "IslandsInTheSky", modid = IslandsInTheSky.MODID, version = IslandsInTheSky.VERSION)
public class IslandsInTheSky {

    public static final String MODID = "islandsinthesky";
    public static final String VERSION = "0.2.2";
    public static IslandData worldData;
    public static WorldType worldType;

    @Mod.Instance("islandsinthesky")
    public static IslandsInTheSky instance;
    @SidedProxy(clientSide = "com.darva.islandsinthesky.proxies.ClientProxy", serverSide = "com.darva.islandsinthesky.proxies.CommonProxy")
    public static CommonProxy proxy;


    public static Logger logger;


    @NetworkCheckHandler
    public boolean checkMods(Map<String, String> mods, Side side)
    {

//        if (side == Side.CLIENT)
//        {
//            System.out.println("This mod should not be run on the client side.");
//            return false;
//        }
        return true;
    }

    @Mod.EventHandler
    public void starting(FMLServerAboutToStartEvent event)
    {
        boolean isVoidWorld = false;

        if (event.getServer().isDedicatedServer()) {
                isVoidWorld=isDedicatedVoidWorld(event.getServer());
        }
        else
        {
            isVoidWorld=isIntegradeVoidWorld(event.getServer());

        }
        if (isVoidWorld)
        {
            DimensionManager.unregisterProviderType(0);
            DimensionManager.registerProviderType(0, VoidWorld.class, true);
        }

    }

    private boolean isIntegradeVoidWorld(MinecraftServer MCserver)
    {
        IntegratedServer server = (IntegratedServer) MCserver;
        Field worldSettings = null;

        try {
            for (Field field : IntegratedServer.class.getDeclaredFields())
            {
                if (field.getGenericType() == WorldSettings.class)
                {
                    worldSettings = field;
                }
            }
            if (worldSettings == null)
            {
                IslandsInTheSky.logger.error("WorldSettings field missing! Probably won't work.");
                return false;
            }

            worldSettings.setAccessible(true);
            WorldSettings settings = (WorldSettings) worldSettings.get(server);
            if (settings.getTerrainType() == IslandsInTheSky.worldType)
                return true;
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    private boolean isDedicatedVoidWorld(MinecraftServer MCserver)
    {
        DedicatedServer server = (DedicatedServer)MCserver;

        if ("IslandsInTheSky".equals(server.getStringProperty("level-type", "")) )
            return true;
        return false;
    }


    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event)
    {
        MinecraftServer server = MinecraftServer.getServer();
        ((ServerCommandManager)(server.getCommandManager())).registerCommand(new IslandCommand());
        ((ServerCommandManager)(server.getCommandManager())).registerCommand(new SetIslandCommand());
        ((ServerCommandManager)(server.getCommandManager())).registerCommand(new InviteCommand());
        ((ServerCommandManager)(server.getCommandManager())).registerCommand(new AcceptCommand());
        if (!server.getCommandManager().getCommands().containsKey("spawn"))
        {
            ((ServerCommandManager)(server.getCommandManager())).registerCommand(new SpawnCommand());
        }

//        if (event.getServer().getConfigurationManager(). == IslandsInTheSky.worldType) {
//            DimensionManager.unregisterProviderType(0);
//            DimensionManager.registerProviderType(0, VoidWorld.class, true);
//
//        }
    }
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        WorldDataHandler handler = new WorldDataHandler();
        MinecraftForge.EVENT_BUS.register(handler);
        MinecraftForge.EVENT_BUS.register(new PlayerHandler());
        MinecraftForge.TERRAIN_GEN_BUS.register(handler);
        worldType = new VoidWorldType();


        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        ConfigurationHolder.getInstance().setupConfigs(config);
        ConfigurationHolder.getInstance().LoadConfigs();
        ConfigurationHolder.getInstance().save();



    }
}
