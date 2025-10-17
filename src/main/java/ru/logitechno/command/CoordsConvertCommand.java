package ru.logitechno.command;

import com.mojang.brigadier.CommandDispatcher;
import dev.xpple.clientarguments.arguments.CBlockPosArgument;
import dev.xpple.clientarguments.arguments.CDimensionArgument;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import ru.logitechno.util.Placeholder;

public class CoordsConvertCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        final var command = dispatcher.register(
                ClientCommandManager.literal("coordsconvert")
                        .then(ClientCommandManager.argument("dimension", CDimensionArgument.dimension())
                                .then(ClientCommandManager.argument("position", CBlockPosArgument.blockPos())
                                        .executes(ctx -> {
                                            var player = ctx.getSource().getPlayer();
                                            if (player == null) return 0;

                                            var dimension = CDimensionArgument.getDimension(ctx, "dimension");
                                            var position = CBlockPosArgument.getBlockPos(ctx, "position");
                                            return convertAndSend(player, dimension, position);
                                        })
                                )
                        )
                        .then(ClientCommandManager.argument("position", CBlockPosArgument.blockPos())
                                .executes(ctx -> {
                                    var player = ctx.getSource().getPlayer();
                                    if (player == null) return 0;

                                    var position = CBlockPosArgument.getBlockPos(ctx, "position");
                                    return convertAndSend(player, null, position);
                                })
                        )
                        .executes(ctx -> {
                            var player = ctx.getSource().getPlayer();
                            if (player == null) return 0;
                            return convertAndSend(player, null, null);
                        })
        );
        dispatcher.register(
                ClientCommandManager.literal("cc")
                        .redirect(command)
                        .executes(ctx -> {
                            var player = ctx.getSource().getPlayer();
                            if (player == null) return 0;
                            return convertAndSend(player, null, null);
                        })
        );
    }

    public static int convertAndSend(ClientPlayerEntity player, RegistryKey<World> world, BlockPos pos) {
        if (world == null) world = player.getEntityWorld().getRegistryKey();

        double x;
        double y;
        double z;

        if (pos == null) {
            Vec3d loc = player.getEntityPos();
            x = loc.getX();
            y = loc.getY();
            z = loc.getZ();
        } else {
            x = pos.getX();
            y = pos.getY();
            z = pos.getZ();
        }

        if (world.equals(World.OVERWORLD)) {
            int netherX = (int) Math.floor(x / 8);
            int netherZ = (int) Math.floor(z / 8);

            player.sendMessage(Placeholder.replacePlaceholders("message.pomidor.overworld",
                            "posX", String.valueOf((int) x),
                            "posY", String.valueOf((int) y),
                            "posZ", String.valueOf((int) z),
                            "netherX", String.valueOf(netherX),
                            "netherZ", String.valueOf(netherZ)
                    ),
                    false);
        } else if (world.equals(World.NETHER)) {
            int overworldX = (int) Math.floor(x * 8);
            int overworldZ = (int) Math.floor(z * 8);

            player.sendMessage(Placeholder.replacePlaceholders("message.pomidor.nether",
                            "posX", String.valueOf((int) x),
                            "posY", String.valueOf((int) y),
                            "posZ", String.valueOf((int) z),
                            "overworldX", String.valueOf(overworldX),
                            "overworldZ", String.valueOf(overworldZ)
                    ),
                    false);
        } else {
            player.sendMessage(Text.translatable("message.pomidor.invalid_world"), false);
        }

        return 1;
    }
}
