package com.kermitemperor.coldsweathands.event;

import com.kermitemperor.coldsweathands.ColdSweatHands;
import com.kermitemperor.coldsweathands.util.ItemInfo;
import com.momosoftworks.coldsweat.api.util.Temperature;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kermitemperor.coldsweathands.ColdSweatHands.LOGGER;


@Mod.EventBusSubscriber(modid = ColdSweatHands.MOD_ID)
public class PlayerActionsEventHandler {

    @SubscribeEvent
    public void blockPlacement(PlayerInteractEvent.RightClickBlock event) {
        Entity entity = event.getEntity();

        if (entity != null && entity.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) entity;
            ItemInfo HandItemInfo = new ItemInfo(player.getMainHandItem().getItem());
            //ItemInfo BlockItemInfo = new ItemInfo(entity.block);
            double tempAroundPlayer = Temperature.get(player, Temperature.Type.WORLD);
            double convertedTempAroundPlayer = Temperature.convertUnits(tempAroundPlayer, Temperature.Units.MC, HandItemInfo.getMeasure(), true);

            //LOGGER.info(BlockItemInfo.getRegistryName().toString());
            LOGGER.info(String.valueOf(convertedTempAroundPlayer));
            LOGGER.info(String.valueOf(HandItemInfo.getMin()));

            if ((null != HandItemInfo.getMin()) && HandItemInfo.getMin() > convertedTempAroundPlayer) {
                event.setUseItem(Event.Result.DENY);
            }


        }

    }

}
