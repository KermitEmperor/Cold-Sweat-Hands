package com.kermitemperor.coldsweathands.event;

import com.kermitemperor.coldsweathands.ColdSweatHands;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = ColdSweatHands.MOD_ID)
public class PlayerActionsEvent {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void blockPlacement(PlayerInteractEvent.RightClickBlock event) {
        Entity entity = event.getEntity();

        if (entity != null && entity.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) entity;
            if (player.getMainHandItem().getItem().equals(Items.STONE)) {
                //double temperature = Temperature.get(player, Temperature.Type.WORLD);
                //player.displayClientMessage(new TextComponent(player.getDisplayName().getString() + ": " + Temperature.convertUnits(temperature, Temperature.Units.MC, Temperature.Units.C, false)), false);
                //player.displayClientMessage(new TextComponent(ConfigHandler.CONFIG.toString()), false);
                event.setUseItem(Event.Result.DENY);
            }
        }

    }

}
