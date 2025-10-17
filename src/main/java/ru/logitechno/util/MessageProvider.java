package ru.logitechno.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class MessageProvider {
    public static ClientPlayerEntity player =  MinecraftClient.getInstance().player;

    public static void sendMessage(String key, boolean enabled) {
        Text enabledString = enabled ? Text.translatable("message.pomidor.on") : Text.translatable("message.pomidor.off");
        player.sendMessage(Text.translatable(key).append(enabledString), true);
    }
}
