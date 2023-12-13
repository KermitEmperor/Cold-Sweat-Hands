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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

import static com.kermitemperor.coldsweathands.ColdSweatHands.LOGGER;

@SuppressWarnings("FieldMayBeFinal")
public class PlayerHUDTempIndicator implements IIngameOverlay  {
    private static int lerp = 0;
    @SuppressWarnings("FieldCanBeLocal")
    private static int distanceFromCenter = -20;
    private static Minecraft mc = Minecraft.getInstance();

    private static final ResourceLocation COLD_BLOCK = new ResourceLocation(ColdSweatHands.MOD_ID, "textures/gui/cold.png");

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {

        int x = width / 2;
        int y = height / 2;

        Player player = mc.player;
        HitResult objectMouseOver = mc.hitResult;

        Block targetBlock;
        if (!(objectMouseOver instanceof BlockHitResult blockHitResult)) {
            return;
        } else {
            targetBlock = player.getLevel().getBlockState(blockHitResult.getBlockPos()).getBlock();
        }
        ItemInfo blockInfo = new ItemInfo(targetBlock.asItem());



        if (blockInfo.getMax() != null) {
            player.displayClientMessage(new TextComponent(String.valueOf(blockInfo.getMax())), true);
            lerp -= lerp > distanceFromCenter ? 1 : 0;
        } else {
            lerp = 0 ;
        }

        if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR || lerp == 0)
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
