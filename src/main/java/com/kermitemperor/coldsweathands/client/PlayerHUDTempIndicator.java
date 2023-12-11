package com.kermitemperor.coldsweathands.client;


import com.kermitemperor.coldsweathands.ColdSweatHands;
import com.kermitemperor.coldsweathands.util.ItemInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kermitemperor.coldsweathands.ColdSweatHands.LOGGER;

@Mod.EventBusSubscriber(modid = ColdSweatHands.MOD_ID)
public class PlayerHUDTempIndicator {
    private static int lerp = 0;
    protected static int distanceFromCenter = -20;


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void PlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        BlockHitResult blockHitResult = getPlayerPOVHitResult(player.getLevel(), player);
        Block targetBlock = player.getLevel().getBlockState(blockHitResult.getBlockPos()).getBlock();
        ItemInfo blockInfo = new ItemInfo(targetBlock.asItem());




        if (blockInfo.getMax() != null) {
            player.displayClientMessage(new TextComponent(String.valueOf(blockInfo.getMax())), true);
            lerp-= lerp > distanceFromCenter ? 1 : 0;
        } else {
            lerp+= lerp < 0 ? 1 : 0;
        }

    }

    public static class PlayerHUDTemperatureIndicator implements IIngameOverlay {
        private static final ResourceLocation COLD_BLOCK = new ResourceLocation(ColdSweatHands.MOD_ID, "textures/gui/cold.png");

        @Override
        public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
            int x = width / 2;
            int y = height / 2;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, Math.abs(lerp/((float)distanceFromCenter)));
            LOGGER.info(String.valueOf(Math.abs(lerp/((float)distanceFromCenter))));
            RenderSystem.setShaderTexture(0, COLD_BLOCK);
            GuiComponent.blit(poseStack, x-16 + lerp,y ,0,0,16,16,16,16);
        }
    }



    //Hippity hoppity your Item class only method is my property
    protected static BlockHitResult getPlayerPOVHitResult(Level pLevel, Player pPlayer) {
        float f = pPlayer.getXRot();
        float f1 = pPlayer.getYRot();
        Vec3 vec3 = pPlayer.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = pPlayer.getReachDistance();
        Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return pLevel.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, pPlayer));
    }
}
