package ru.logitechno;

import com.mojang.brigadier.CommandDispatcher;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.logitechno.command.CoordsConvertCommand;
import ru.logitechno.config.PomidorConfig;
import ru.logitechno.event.UseEntityListener;
import ru.logitechno.event.KeyInputHandler;

public class Pomidor implements ModInitializer {
	public static final String MOD_ID = "pomidor";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static PomidorConfig CONFIG;

	@Override
	public void onInitialize() {
		AutoConfig.register(PomidorConfig.class, JanksonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(PomidorConfig.class).getConfig();

        KeyInputHandler.register();
        UseEntityListener.register();

		ClientCommandRegistrationCallback.EVENT.register(Pomidor::registerCommands);

		HudElementRegistry.attachElementBefore(
				VanillaHudElements.CHAT,
				Identifier.fromNamespaceAndPath(MOD_ID, "before_chat"),
				HUDHandler::render
		);
	}

	private static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext buildContext) {
		CoordsConvertCommand.register(dispatcher);
	}
}