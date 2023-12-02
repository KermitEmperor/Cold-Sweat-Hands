package com.kermitemperor.coldsweathands.event;

import com.kermitemperor.coldsweathands.ColdSweatHands;
import com.kermitemperor.coldsweathands.config.ConfigHandler;
import com.kermitemperor.coldsweathands.networking.PacketChannel;
import com.kermitemperor.coldsweathands.networking.packet.JSONPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kermitemperor.coldsweathands.ColdSweatHands.LOGGER;
import static com.kermitemperor.coldsweathands.config.ConfigHandler.*;

@Mod.EventBusSubscriber(modid = ColdSweatHands.MOD_ID)
public class ConfigReloadEventHandler {
    @SubscribeEvent
    @OnlyIn(Dist.DEDICATED_SERVER)
    void onServerReload(OnDatapackSyncEvent event) {
        try {
            PacketChannel.sendToAllClients(new JSONPacket(ConfigHandler.reloadConfigAndReturn()));
            LOGGER.info("Config reloading and sharing was successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.DEDICATED_SERVER)
    void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        try {
            Player player = event.getPlayer();
            PacketChannel.sendToClient(new JSONPacket(CONFIG), (ServerPlayer) player);
            LOGGER.info("Config sharing with %s (%s) was successful".formatted(player.getName().getContents(), player.getStringUUID()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
