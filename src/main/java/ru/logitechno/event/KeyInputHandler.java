package ru.logitechno.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import ru.logitechno.Pomidor;
import ru.logitechno.util.MessageProvider;

import static ru.logitechno.config.PomidorConfigModel.Choices.*;
import static ru.logitechno.config.PomidorConfigModel.Choices.DEFAULT;

public class KeyInputHandler {
    public static final KeyMapping.Category POMIDOR_CATEGORY = KeyMapping.Category.register(Identifier.parse("pomidor"));
    public static KeyMapping toggleVisibleLightBlockKey;
    public static KeyMapping toggleForceShowNameTagsRenderKey;
    public static KeyMapping togglePlayersNameTagsRenderKey;

    public static void register() {
        toggleVisibleLightBlockKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.pomidor.toggleVisibleLightBlock",
                InputConstants.Type.KEYSYM,
                85,
                POMIDOR_CATEGORY
        ));

        toggleForceShowNameTagsRenderKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.pomidor.toggleForceShowMobNameTagsRender",
                InputConstants.Type.KEYSYM,
                79,
                POMIDOR_CATEGORY
        ));

        togglePlayersNameTagsRenderKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.pomidor.togglePlayersNameTagsRender",
                InputConstants.Type.KEYSYM,
                73,
                POMIDOR_CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleVisibleLightBlockKey.consumeClick()) {
                Minecraft.getInstance().levelRenderer.allChanged();
                Pomidor.CONFIG.isLightBlockVisible(!Pomidor.CONFIG.isLightBlockVisible());
                MessageProvider.sendMessage(
                        "message.pomidor.visibleLightBlockRender",
                        Pomidor.CONFIG.isLightBlockVisible()
                );
            }

            while (toggleForceShowNameTagsRenderKey.consumeClick()) {
                Pomidor.CONFIG.forceShowMobNameTags(!Pomidor.CONFIG.forceShowMobNameTags());
                MessageProvider.sendMessage(
                        "message.pomidor.forceShowMobNameTagsRender",
                        Pomidor.CONFIG.forceShowMobNameTags()
                );
            }

            while (togglePlayersNameTagsRenderKey.consumeClick()) {
                switch (Pomidor.CONFIG.playersNameTags()) {
                    case DEFAULT:
                        Pomidor.CONFIG.playersNameTags(HIDE);
                        break;
                    case HIDE:
                        Pomidor.CONFIG.playersNameTags(FORCE);
                        break;
                    default:
                        Pomidor.CONFIG.playersNameTags(DEFAULT);
                }
                var player = Minecraft.getInstance().player;
                if (player == null) return;
                player.sendOverlayMessage(
                        Component.translatable("message.pomidor.playersNameTagsRender").append(Component.literal(Pomidor.CONFIG.playersNameTags().name()))
                );
            }
        });
    }
}
