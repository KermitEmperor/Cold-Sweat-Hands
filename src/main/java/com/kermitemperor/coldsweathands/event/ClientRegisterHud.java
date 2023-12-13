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
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            final Minecraft mc = Minecraft.getInstance();

            int width = event.getWindow().getGuiScaledWidth();
            int height = event.getWindow().getGuiScaledHeight();


            new PlayerHUDTempIndicator.PlayerHUDTemperatureIndicator().render(
                    new ForgeIngameGui(mc),
                    event.getMatrixStack(),
                    event.getPartialTicks(),
                    width,
                    height
            );
        }
    }
}
