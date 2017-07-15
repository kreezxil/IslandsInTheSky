//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gmail.zendarva.islands;

import com.gmail.zendarva.islands.ConfigurationHolder;
import com.gmail.zendarva.islands.IslandData;
import com.gmail.zendarva.islands.command.AcceptCommand;
import com.gmail.zendarva.islands.command.InviteCommand;
import com.gmail.zendarva.islands.command.IslandCommand;
import com.gmail.zendarva.islands.command.SetIslandCommand;
import com.gmail.zendarva.islands.command.SpawnCommand;
import com.gmail.zendarva.islands.generation.SkyIslandWorldProvider;
import com.gmail.zendarva.islands.generation.SkyIslandWorldType;
import com.gmail.zendarva.islands.generation.SkyNetherWorldProvider;
import com.gmail.zendarva.islands.handlers.CapabilityHandler;
import com.gmail.zendarva.islands.handlers.ProtectionHandler;
import com.gmail.zendarva.islands.handlers.WorldDataHandler;
import com.gmail.zendarva.islands.proxy.CommonProxy;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = "islandsinthesky",
        version = "2.0",
        acceptedMinecraftVersions = "[1.12]"
)
public class Islands {
    public static final String MODID = "islandsinthesky";
    public static final String VERSION = "2.0";
    public static IslandData worldData;
    static WorldType type = new SkyIslandWorldType();
    public static Logger logger;
    @SidedProxy(
            clientSide = "com.gmail.zendarva.islands.proxy.ClientProxy",
            serverSide = "com.gmail.zendarva.islands.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    public Islands() {
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        DimensionManager.unregisterDimension(0);
        DimensionManager.unregisterDimension(-1);
        DimensionManager.registerDimension(0, DimensionType.register("Sky Island", "", 0, SkyIslandWorldProvider.class, true));
        DimensionManager.registerDimension(-1, DimensionType.register("Sky Nether", "", 0, SkyNetherWorldProvider.class, true));
    }

    @SubscribeEvent
    public void onWorldLoad(Load event) {
        if(!event.getWorld().isRemote && event.getWorld() instanceof WorldServer) {
            WorldServer world = (WorldServer)event.getWorld();
            int spawnX = (int)((double)event.getWorld().getWorldInfo().getSpawnX() / world.provider.getMovementFactor() / 16.0D);
            int spawnZ = (int)((double)event.getWorld().getWorldInfo().getSpawnZ() / world.provider.getMovementFactor() / 16.0D);

            for(int x = -1; x <= 1; ++x) {
                for(int z = -1; z <= 1; ++z) {
                    world.getChunkProvider().loadChunk(spawnX + x, spawnZ + z);
                }
            }
        }

    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.init();
        WorldDataHandler handler = new WorldDataHandler();
        MinecraftForge.EVENT_BUS.register(handler);
        CapabilityHandler cHandler = new CapabilityHandler();
        MinecraftForge.EVENT_BUS.register(cHandler);
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        ConfigurationHolder.getInstance().setupConfigs(config);
        ConfigurationHolder.getInstance().LoadConfigs();
        ConfigurationHolder.getInstance().save();
        if(ConfigurationHolder.protectIslands) {
            MinecraftForge.EVENT_BUS.register(new ProtectionHandler());
        }

    }

    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        ((ServerCommandManager)((ServerCommandManager)server.getCommandManager())).registerCommand(new IslandCommand());
        ((ServerCommandManager)((ServerCommandManager)server.getCommandManager())).registerCommand(new SetIslandCommand());
        ((ServerCommandManager)((ServerCommandManager)server.getCommandManager())).registerCommand(new InviteCommand());
        ((ServerCommandManager)((ServerCommandManager)server.getCommandManager())).registerCommand(new AcceptCommand());
        if(!server.getCommandManager().getCommands().containsKey("spawn")) {
            ((ServerCommandManager)((ServerCommandManager)server.getCommandManager())).registerCommand(new SpawnCommand());
        }

    }

    public static boolean shouldBeVoid(World world) {
        return world.getWorldInfo().getTerrainType() == type;
    }
}
