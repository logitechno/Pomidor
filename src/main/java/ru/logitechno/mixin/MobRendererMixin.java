package ru.logitechno.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.logitechno.Pomidor;

@Mixin(MobRenderer.class)
public class MobRendererMixin<T extends Mob> {
	@Inject(
            method = "shouldShowName(Lnet/minecraft/world/entity/Mob;D)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onShouldShowName(T mobEntity, double d, CallbackInfoReturnable<Boolean> cir) {
        if (Pomidor.CONFIG.forceShowMobNameTags()) {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            if (mobEntity.hasCustomName() && mobEntity.distanceTo(player) <= 32) {
                cir.setReturnValue(true);
            }
        }
    }
}