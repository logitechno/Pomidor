package ru.logitechno.util;


import net.minecraft.text.Text;

public class Placeholder {
    public static Text replacePlaceholders(String key, String... placeholders) {
        if (placeholders.length % 2 != 0) {
            return Text.translatable(key);
        }

        String message = Text.translatable(key).getString();

        for (int i = 0; i < placeholders.length; i += 2) {
            message = message.replace("{" + placeholders[i] + "}", placeholders[i + 1]);
        }

        return Text.literal(message);
    }

    public static Text replacePlaceholders(Text key, String... placeholders) {
        return replacePlaceholders(key.getString(), placeholders);
    }

    public static Text replaceStringPlaceholders(String key, String... placeholders) {
        if (placeholders.length % 2 != 0) {
            return Text.literal(key);
        }

        String message = key;

        for (int i = 0; i < placeholders.length; i += 2) {
            message = message.replace("{" + placeholders[i] + "}", placeholders[i + 1]);
        }

        return Text.literal(message);
    }
}
