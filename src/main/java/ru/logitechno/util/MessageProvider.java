package ru.logitechno.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class MessageProvider {
    public static Player player = Minecraft.getInstance().player;

    public static void sendMessage(String key, boolean enabled) {
        Component enabledString = enabled ?
                Component.translatable("message.pomidor.on") :
                Component.translatable("message.pomidor.off");
        player.sendOverlayMessage(Component.translatable(key).append(enabledString));
    }
}
