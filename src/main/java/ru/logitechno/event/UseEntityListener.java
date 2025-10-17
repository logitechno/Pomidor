package ru.logitechno.event;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.ActionResult;
import ru.logitechno.util.Converter;
import ru.logitechno.util.Placeholder;

import java.text.DecimalFormat;

public class UseEntityListener {
    public static void register() {
        UseEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            if (!playerEntity.isSneaking()) return ActionResult.PASS;

            LivingEntity livingEntity = entity.getEntity();
            if (livingEntity instanceof AbstractHorseEntity || livingEntity instanceof PigEntity) {
                DecimalFormat df = new DecimalFormat("#.#");
                String jumpHeight = df.format(Converter.jumpStrengthToJumpHeight(livingEntity.getAttributeInstance(EntityAttributes.JUMP_STRENGTH).getValue()));
                String maxHealth = df.format(livingEntity.getMaxHealth());
                String movementSpeed = df.format(Converter.genericSpeedToBlocPerSec(livingEntity.getAttributes().getValue(EntityAttributes.MOVEMENT_SPEED)));

                playerEntity.sendMessage(Placeholder.replacePlaceholders("text.pomidor.horse_stats",
                                "speed", movementSpeed,
                                "jump", jumpHeight,
                                "health", maxHealth
                        ), true);
            }

            return ActionResult.PASS;
        });
    }
}
