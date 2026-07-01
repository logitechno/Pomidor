package ru.logitechno.util;

import net.minecraft.network.chat.Component;

public class Placeholder {
    public record PlaceholderPair(String key, String value) {}

    public static Component replacePlaceholders(String text, PlaceholderPair... placeholders) {
        String message = Component.translatable(text).getString().replace("&", "§");

        for (PlaceholderPair placeholderPair : placeholders) {
            message = message.replace("{" + placeholderPair.key + "}", placeholderPair.value);
        }

        return Component.literal(message);
    }
}
