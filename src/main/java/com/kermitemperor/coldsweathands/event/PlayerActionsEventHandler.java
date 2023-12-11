package com.kermitemperor.coldsweathands.event;

import com.kermitemperor.coldsweathands.ColdSweatHands;
import com.kermitemperor.coldsweathands.util.ItemInfo;
import com.momosoftworks.coldsweat.api.util.Temperature;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = ColdSweatHands.MOD_ID)
public class PlayerActionsEventHandler {

    @SubscribeEvent
    public void blockPlacementItemUse(PlayerInteractEvent.RightClickBlock event) {
        Entity entity = event.getEntity();

        if (entity != null && entity.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) entity;
            double tempAroundPlayer = Temperature.get(player, Temperature.Type.WORLD);

            ItemInfo handItemInfo = new ItemInfo(player.getMainHandItem().getItem());
            Block targetBlock = player.getLevel().getBlockState(event.getHitVec().getBlockPos()).getBlock();
            ItemInfo targetBlockInfo = new ItemInfo(targetBlock.asItem());



            if (handItemInfo.getInfo() != null) {
                double convertedTempAroundPlayer = Temperature.convertUnits(tempAroundPlayer, Temperature.Units.MC, handItemInfo.getMeasure(), true);
                if ((null != handItemInfo.getMin()) && handItemInfo.getMin() > convertedTempAroundPlayer) {
                    event.setUseItem(Event.Result.DENY);
                }
                if ((null != handItemInfo.getMax()) && handItemInfo.getMax() < convertedTempAroundPlayer) {
                    event.setUseItem(Event.Result.DENY);
                }
            }
            if (targetBlockInfo.getInfo() != null) {
                double convertedTempAroundPlayer = Temperature.convertUnits(tempAroundPlayer, Temperature.Units.MC, targetBlockInfo.getMeasure(), true);
                if ((targetBlockInfo.getClickable() != null) && targetBlockInfo.getClickable()) {
                    if ((null != targetBlockInfo.getMin()) && targetBlockInfo.getMin() > convertedTempAroundPlayer) {
                        event.setUseBlock(Event.Result.DENY);
                    }
                    if ((null != targetBlockInfo.getMax()) && targetBlockInfo.getMax() < convertedTempAroundPlayer) {
                        event.setUseBlock(Event.Result.DENY);
                    }
                }
            }

        }

    }

}
