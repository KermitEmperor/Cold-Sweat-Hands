package com.kermitemperor.coldsweathands.util;

import com.kermitemperor.coldsweathands.config.ConfigHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;


public class SinglePlayerReloadListener extends SimplePreparableReloadListener<Void> {
    public SinglePlayerReloadListener() {
    }

    @Override
    @NotNull
    protected Void prepare(@NotNull ResourceManager pResourceManager, @NotNull ProfilerFiller pProfiler) {
        return null;
    }


    @Override
    protected void apply(@NotNull Void pObject, @NotNull ResourceManager pResourceManager, @NotNull ProfilerFiller pProfiler) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if (server == null) return;
        if (!server.isSingleplayer()) return;

        ConfigHandler.reloadConfig();


    }

}
