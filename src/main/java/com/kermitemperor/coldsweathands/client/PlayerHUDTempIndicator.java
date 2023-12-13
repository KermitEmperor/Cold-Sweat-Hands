package com.kermitemperor.coldsweathands.client;


import com.kermitemperor.coldsweathands.ColdSweatHands;
import com.kermitemperor.coldsweathands.util.ItemInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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

    @SuppressWarnings("FieldMayBeFinal")
    private static Minecraft mc = Minecraft.getInstance();


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void PlayerTick(TickEvent.PlayerTickEvent event) {
        BlockHitResult blockHitResult;
        Block targetBlock;

        Player player = event.player;
        HitResult objectMouseOver = mc.hitResult;

        if (!(objectMouseOver instanceof BlockHitResult)) {
            targetBlock = Blocks.AIR;
        } else {
            blockHitResult = (BlockHitResult) objectMouseOver;
            targetBlock = player.getLevel().getBlockState(blockHitResult.getBlockPos()).getBlock();
        }
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

        @SuppressWarnings("DataFlowIssue")
        @Override
        public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
            int x = width / 2;
            int y = height / 2;

            if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR)
                return;

            poseStack.pushPose();

            poseStack.translate(lerp, 0, 0);

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, Math.abs(lerp/((float)distanceFromCenter)));
            RenderSystem.setShaderTexture(0, COLD_BLOCK);
            GuiComponent.blit(poseStack, x-16,y ,0,0,16,16,16,16);

            poseStack.popPose();
        }
    }

}
