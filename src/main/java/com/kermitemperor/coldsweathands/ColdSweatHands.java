package com.kermitemperor.coldsweathands;

import com.kermitemperor.coldsweathands.config.ConfigHandler;
import com.kermitemperor.coldsweathands.event.PlayerActionsEvent;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ColdSweatHands.MOD_ID)
public class ColdSweatHands {

    // Directly reference a slf4j logger
    public static final String MOD_ID = "coldsweathands";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ColdSweatHands() {
        // Register the setup method for modloading
        IEventBus ModEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEventBus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        IEventBus ForgeEventBus = MinecraftForge.EVENT_BUS;
        ForgeEventBus.register(this);
        ForgeEventBus.register(new PlayerActionsEvent());
        ForgeEventBus.register(new ConfigHandler.ReloadEvent());

    }

    private void setup(final FMLCommonSetupEvent event) {
        // Some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        ConfigHandler.init();
    }

}
