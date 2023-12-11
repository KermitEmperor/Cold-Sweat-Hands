package com.kermitemperor.coldsweathands.event;


import com.kermitemperor.coldsweathands.ColdSweatHands;
import com.kermitemperor.coldsweathands.client.PlayerHUDTempIndicator;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kermitemperor.coldsweathands.ColdSweatHands.LOGGER;

@Mod.EventBusSubscriber(modid = ColdSweatHands.MOD_ID)
public class ClientRegisterHud {

    @SubscribeEvent
    void registerGuiOverlays(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            final Minecraft mc = Minecraft.getInstance();
            int minecraftGuiScale = mc.options.guiScale;
            minecraftGuiScale = (minecraftGuiScale != 0 ? minecraftGuiScale : 1);

            int width = event.getWindow().getWidth();
            int height = event.getWindow().getHeight();


            new PlayerHUDTempIndicator.PlayerHUDTemperatureIndicator().render(
                    new ForgeIngameGui(mc),
                    event.getMatrixStack(),
                    event.getPartialTicks(),
                    width / minecraftGuiScale,
                    height / minecraftGuiScale
            );
        }
    }
}
