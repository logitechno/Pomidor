package ru.logitechno.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.logitechno.Pomidor;

@Mixin(MobEntityRenderer.class)
public class EntityRenderNameMixin<T extends MobEntity> {
	@Inject(
            method = "hasLabel(Lnet/minecraft/entity/mob/MobEntity;D)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void hasLabel(T mobEntity, double d, CallbackInfoReturnable<Boolean> cir) {
        if (Pomidor.CONFIG.forceShowNameTags()) {
            PlayerEntity player = MinecraftClient.getInstance().player;
            if (mobEntity.hasCustomName() && mobEntity.isInRange(player, 32)) {
                cir.setReturnValue(true);
            }
        }
    }
}