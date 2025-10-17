package ru.logitechno.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import ru.logitechno.Pomidor;
import ru.logitechno.util.MessageProvider;

public class KeyInputHandler {
    public static final KeyBinding.Category POMIDOR_CATEGORY = KeyBinding.Category.create(Identifier.of("pomidor"));
    public static KeyBinding toggleVisibleLightBlockKey;
    public static KeyBinding toggleForceShowNameTagsRenderKey;
    public static KeyBinding togglePlayersNameTagsRenderKey;

    public static void register() {
        toggleVisibleLightBlockKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pomidor.toggleVisibleLightBlock", InputUtil.Type.KEYSYM, 85, POMIDOR_CATEGORY
        ));
        toggleForceShowNameTagsRenderKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pomidor.toggleForceShowNameTagsRender", InputUtil.Type.KEYSYM, 79, POMIDOR_CATEGORY
        ));
        togglePlayersNameTagsRenderKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pomidor.togglePlayersNameTagsRender", InputUtil.Type.KEYSYM, 73, POMIDOR_CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleVisibleLightBlockKey.wasPressed()) {
                MinecraftClient.getInstance().worldRenderer.reload();
                Pomidor.CONFIG.isLightBlockVisible(!Pomidor.CONFIG.isLightBlockVisible());
                MessageProvider.sendMessage("message.pomidor.visibleLightBlockRender", Pomidor.CONFIG.isLightBlockVisible());
            }

            while (toggleForceShowNameTagsRenderKey.wasPressed()) {
                Pomidor.CONFIG.forceShowNameTags(!Pomidor.CONFIG.forceShowNameTags());
                MessageProvider.sendMessage("message.pomidor.forceShowNameTagsRender", Pomidor.CONFIG.forceShowNameTags());
            }

            while (togglePlayersNameTagsRenderKey.wasPressed()) {
                Pomidor.CONFIG.hidePlayersNameTags(!Pomidor.CONFIG.hidePlayersNameTags());
                MessageProvider.sendMessage("message.pomidor.playersNameTagsRender", Pomidor.CONFIG.hidePlayersNameTags());
            }
        });
    }
}