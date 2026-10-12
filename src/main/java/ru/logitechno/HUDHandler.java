package ru.logitechno;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import ru.logitechno.util.Placeholder;

public class HUDHandler {
    public static void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (!Pomidor.CONFIG.showHUDInfo) return;

        Minecraft mc = Minecraft.getInstance();
        Font textRenderer = mc.font;

        Player player = mc.player;
        ClientLevel world = mc.level;

        if (player == null || world == null) return;

        int posX = (int) player.getX();
        int posY = (int) player.getY();
        int posZ = (int) player.getZ();

        long time = world.getOverworldClockTime();
        long hours = (time / 1000L + 6L) % 24L;
        long minutes = (time % 1000L) * 60L / 1000L;
        String timeString = String.format("%02d:%02d", hours, minutes);

        String biome = Component.translatable(world.getBiome(player.blockPosition()).unwrapKey().get().identifier().toLanguageKey("biome")).getString();

        ClientPacketListener networkHandler = mc.getConnection();
        PlayerInfo playerListEntry = networkHandler != null ? networkHandler.getPlayerInfo(player.getUUID()) : null;
        int ping = playerListEntry != null ? playerListEntry.getLatency() : 0;

        String difficulty = world.getDifficulty().getDisplayName().getString();

        String dimension = Component.translatable("dimension." + player.level().dimensionTypeRegistration().unwrapKey().get().identifier().toLanguageKey()).getString();

        double dx = player.getX() - player.xOld;
        double dy = player.getY() - player.yOld;
        double dz = player.getZ() - player.zOld;
        String speed = String.format("%.2f", Math.sqrt(dx * dx + dy * dy + dz * dz) * 20);

        float yaw = Mth.wrapDegrees(player.getYRot());
        float pitch = Mth.wrapDegrees(player.getXRot());

        Direction direction = player.getDirection();
        String facing = getFacing(direction);

        Component message = Placeholder.replacePlaceholders(Pomidor.CONFIG.textHUDInfo,
                new Placeholder.PlaceholderPair("posX", String.valueOf(posX)),
                new Placeholder.PlaceholderPair("posY", String.valueOf(posY)),
                new Placeholder.PlaceholderPair("posZ", String.valueOf(posZ)),
                new Placeholder.PlaceholderPair("time", timeString),
                new Placeholder.PlaceholderPair("biome", biome),
                new Placeholder.PlaceholderPair("difficulty", difficulty),
                new Placeholder.PlaceholderPair("days", String.valueOf(world.getGameTime() / 24000)),
                new Placeholder.PlaceholderPair("ping", String.valueOf(ping)),
                new Placeholder.PlaceholderPair("pingColored", getPingColor(ping) + ping),
                new Placeholder.PlaceholderPair("fps", String.valueOf(mc.getFps())),
                new Placeholder.PlaceholderPair("dimension", dimension),
                new Placeholder.PlaceholderPair("speed", speed),
                new Placeholder.PlaceholderPair("facing", facing),
                new Placeholder.PlaceholderPair("yaw", String.format("%.1f", yaw)),
                new Placeholder.PlaceholderPair("pitch", String.format("%.1f", pitch)),
                new Placeholder.PlaceholderPair("n", "\n")
        );

        graphics.textWithWordWrap(textRenderer, message, Pomidor.CONFIG.offsetXHUDInfo, Pomidor.CONFIG.offsetZHUDInfo, 200, 0xFFFFFFFF);
    }

    private static String getFacing(Direction direction) {
        return switch (direction) {
            case Direction.NORTH -> "NORTH (-Z)";
            case Direction.SOUTH -> "SOUTH (+Z)";
            case Direction.WEST -> "WEST (-X)";
            case Direction.EAST -> "EAST (+X)";
            default -> "Invalid";
        };
    }

    private static String getPingColor(int ping) {
        if (ping < 50) return "§a";
        if (ping < 100) return "§e";
        if (ping < 200) return "§6";
        return "§c";
    }
}
