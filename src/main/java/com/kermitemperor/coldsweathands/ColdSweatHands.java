package com.kermitemperor.coldsweathands;

import com.kermitemperor.coldsweathands.config.ConfigHandler;
import com.kermitemperor.coldsweathands.event.ClientRegisterHud;
import com.kermitemperor.coldsweathands.event.ConfigReloadEventHandler;
import com.kermitemperor.coldsweathands.event.PlayerActionsEventHandler;
import com.kermitemperor.coldsweathands.networking.PacketChannel;
import com.kermitemperor.coldsweathands.util.SinglePlayerReloadListener;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ColdSweatHands.MOD_ID)
public class ColdSweatHands {

    public static final String MOD_ID = "coldsweathands";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ColdSweatHands() {
        IEventBus ModEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEventBus.addListener(this::setup);


        IEventBus ForgeEventBus = MinecraftForge.EVENT_BUS;
        ForgeEventBus.addListener(this::addReloadListener);
        ForgeEventBus.register(this);
        ForgeEventBus.register(new PlayerActionsEventHandler());
        ForgeEventBus.register(new ConfigReloadEventHandler());
        ForgeEventBus.register(new ClientRegisterHud());



    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        ConfigHandler.init();
        event.enqueueWork(PacketChannel::register);

    }

    private void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(new SinglePlayerReloadListener());
    }

}
