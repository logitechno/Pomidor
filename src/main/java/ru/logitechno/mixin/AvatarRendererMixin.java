package ru.logitechno.mixin;

import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.logitechno.Pomidor;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {

    @Inject(
            method = "shouldShowName(Lnet/minecraft/world/entity/Avatar;D)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onShouldShowName(CallbackInfoReturnable<Boolean> cir) {
        switch (Pomidor.CONFIG.playersNameTags()) {
            case HIDE:
                cir.setReturnValue(false);
                cir.cancel();
                break;
            case FORCE:
                cir.setReturnValue(true);
                cir.cancel();
        }
    }
}
