package ru.logitechno.event;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.pig.Pig;
import ru.logitechno.util.Converter;
import ru.logitechno.util.Placeholder;

import java.text.DecimalFormat;

public class UseEntityListener {
    public static void register() {
        UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (!player.isShiftKeyDown()) return InteractionResult.PASS;

            if (!(entity instanceof LivingEntity livingEntity)) return InteractionResult.PASS;
            if (livingEntity instanceof AbstractHorse || livingEntity instanceof Pig) {
                DecimalFormat df = new DecimalFormat("#.#");
                String jumpHeight = df.format(Converter.jumpStrengthToJumpHeight(livingEntity.getAttribute(Attributes.JUMP_STRENGTH).getValue()));
                String maxHealth = df.format(livingEntity.getMaxHealth());
                String movementSpeed = df.format(Converter.genericSpeedToBlocPerSec(livingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED)));

                player.sendOverlayMessage(
                        Placeholder.replacePlaceholders(
                                "text.pomidor.horse_stats",
                                new Placeholder.PlaceholderPair("speed", movementSpeed),
                                new Placeholder.PlaceholderPair("jump", jumpHeight),
                                new Placeholder.PlaceholderPair("health", maxHealth)
                        )
                );

                return InteractionResult.PASS;
            }

            return InteractionResult.PASS;
        });
    }
}
