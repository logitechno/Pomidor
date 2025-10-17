package ru.logitechno;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.logitechno.command.CoordsConvertCommand;
import ru.logitechno.config.PomidorConfig;
import ru.logitechno.event.KeyInputHandler;
import ru.logitechno.event.UseEntityListener;

public class Pomidor implements ClientModInitializer {
    public static final String MOD_ID = "pomidor";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final PomidorConfig CONFIG = PomidorConfig.createAndLoad();


    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        UseEntityListener.register();

        BlockRenderLayerMap.putBlock(Blocks.LIGHT, BlockRenderLayer.TRANSLUCENT);

        ClientCommandRegistrationCallback.EVENT.register(Pomidor::registerCommands);

        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.of(MOD_ID, "before_chat"), HUDHandler::render);
    }

    private static void registerCommands(CommandDispatcher<FabricClientCommandSource> fabricClientCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess) {
        CoordsConvertCommand.register(fabricClientCommandSourceCommandDispatcher);
    }
}