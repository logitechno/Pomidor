package ru.logitechno.command;

import com.mojang.brigadier.CommandDispatcher;
import dev.xpple.clientarguments.arguments.CBlockPosArgument;
import dev.xpple.clientarguments.arguments.CDimensionArgument;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import ru.logitechno.util.Placeholder;

public class CoordsConvertCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        final var command = dispatcher.register(
                ClientCommands.literal("coordsconvert")
                        .then(ClientCommands.argument("dimension", CDimensionArgument.dimension())
                                .then(ClientCommands.argument("position", CBlockPosArgument.blockPos())
                                        .executes(ctx -> {
                                            var player = ctx.getSource().getPlayer();

                                            var dimension = CDimensionArgument.getDimension(ctx, "dimension");
                                            var position = CBlockPosArgument.getBlockPos(ctx, "position");
                                            return convertAndSend(player, dimension, position);
                                        })
                                )
                        )
                        .then(ClientCommands.argument("position", CBlockPosArgument.blockPos())
                                .executes(ctx -> {
                                    var player = ctx.getSource().getPlayer();

                                    var position = CBlockPosArgument.getBlockPos(ctx, "position");
                                    return convertAndSend(player, null, position);
                                })
                        )
                        .executes(ctx -> {
                            var player = ctx.getSource().getPlayer();
                            return convertAndSend(player, null, null);
                        })
        );
        dispatcher.register(
                ClientCommands.literal("cc")
                        .redirect(command)
                        .executes(ctx -> {
                            var player = ctx.getSource().getPlayer();
                            return convertAndSend(player, null, null);
                        })
        );
    }

    public static int convertAndSend(Player player, ResourceKey<Level> level, BlockPos pos) {
        if (level == null) level = player.level().dimension();

        double x;
        double y;
        double z;

        if (pos == null) {
            x = player.getX();
            y = player.getY();
            z = player.getZ();
        } else {
            x = pos.getX();
            y = pos.getY();
            z = pos.getZ();
        }

        if (level.equals(Level.OVERWORLD)) {
            int netherX = (int) Math.floor(x / 8);
            int netherZ = (int) Math.floor(z / 8);

            player.sendSystemMessage(Placeholder.replacePlaceholders("message.pomidor.overworld",
                    new Placeholder.PlaceholderPair("posX", String.valueOf((int) x)),
                    new Placeholder.PlaceholderPair("posY", String.valueOf((int) y)),
                    new Placeholder.PlaceholderPair("posZ", String.valueOf((int) z)),
                    new Placeholder.PlaceholderPair("netherX", String.valueOf(netherX)),
                    new Placeholder.PlaceholderPair("netherZ", String.valueOf(netherZ))
            ));
        } else if (level.equals(Level.NETHER)) {
            int overworldX = (int) Math.floor(x * 8);
            int overworldZ = (int) Math.floor(z * 8);

            player.sendSystemMessage(Placeholder.replacePlaceholders("message.pomidor.nether",
                    new Placeholder.PlaceholderPair("posX", String.valueOf((int) x)),
                    new Placeholder.PlaceholderPair("posY", String.valueOf((int) y)),
                    new Placeholder.PlaceholderPair("posZ", String.valueOf((int) z)),
                    new Placeholder.PlaceholderPair("overworldX", String.valueOf(overworldX)),
                    new Placeholder.PlaceholderPair("overworldZ", String.valueOf(overworldZ))
            ));
        } else {
            player.sendSystemMessage(Component.translatable("message.pomidor.invalid_world"));
        }

        return 1;
    }
}
