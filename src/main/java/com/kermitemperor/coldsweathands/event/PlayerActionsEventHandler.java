package com.kermitemperor.coldsweathands.event;

import com.kermitemperor.coldsweathands.ColdSweatHands;
import com.kermitemperor.coldsweathands.config.ConfigHandler;
import com.momosoftworks.coldsweat.api.util.Temperature;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = ColdSweatHands.MOD_ID)
public class PlayerActionsEventHandler {

    @SubscribeEvent
    public void blockPlacement(PlayerInteractEvent.RightClickBlock event) {
        Entity entity = event.getEntity();

        if (entity != null && entity.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) entity;
            if (player.getMainHandItem().getItem().equals(Items.STONE)) {
                double temperature = Temperature.get(player, Temperature.Type.WORLD);
                player.displayClientMessage(new TextComponent(player.getDisplayName().getString() + ": " + Temperature.convertUnits(temperature, Temperature.Units.MC, Temperature.Units.C, false)), false);
                player.displayClientMessage(new TextComponent(ConfigHandler.CONFIG + event.getSide().name()), false);
                if (ConfigHandler.CONFIG.get("Test").getAsBoolean()) {
                    event.setUseItem(Event.Result.DENY);
                }
            }
        }

    }

}
