package ru.logitechno;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import ru.logitechno.util.Placeholder;

public class HUDHandler {
    public static void render(DrawContext context, RenderTickCounter tickCounter) {
        if (!Pomidor.CONFIG.toggleHUDInfo()) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        TextRenderer textRenderer = mc.textRenderer;

        ClientPlayerEntity player = mc.player;
        ClientWorld world = mc.world;

        if (player == null || world == null) return;

        int posX = (int) player.getX();
        int posY = (int) player.getY();
        int posZ = (int) player.getZ();

        long time = world.getTimeOfDay();
        long hours = (time / 1000L + 6L) % 24L;
        long minutes = (time % 1000L) * 60L / 1000L;
        String timeString = String.format("%02d:%02d", hours, minutes);

        String biome = Text.translatable(world.getBiome(player.getBlockPos()).getKey().get().getValue().toTranslationKey("biome")).getString();

        Text message = Placeholder.replaceStringPlaceholders(Pomidor.CONFIG.textHUDInfo(),
                "posX", String.valueOf(posX),
                "posY", String.valueOf(posY),
                "posZ", String.valueOf(posZ),
                "time", timeString,
                "biome", biome,
                "n", "\n"
        );

        context.drawWrappedTextWithShadow(textRenderer, message, Pomidor.CONFIG.intendXHUDInfo(), Pomidor.CONFIG.intendZHUDInfo(), 200, 0xFFFFFFFF);
    }
}
