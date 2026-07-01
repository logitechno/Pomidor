package ru.logitechno.mixin;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.logitechno.Pomidor;
import ru.logitechno.util.Placeholder;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Inject(method = "handlePlayerCombatKill", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;shouldShowDeathScreen()Z"))
    private void onHandlePlayerCombatKill(ClientboundPlayerCombatKillPacket packet, CallbackInfo ci) {
        if (!RenderSystem.isOnRenderThread()) return;
        if (Pomidor.CONFIG.showDeathMessage()) {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            int posX = (int) player.getX();
            int posY = (int) player.getY();
            int posZ = (int) player.getZ();
            String dimension = Component.translatable("dimension." + player.level().dimensionTypeRegistration().unwrapKey().get().identifier().toLanguageKey()).getString();

            Component message = Placeholder.replacePlaceholders(Pomidor.CONFIG.textDeathMessage(),
                    new Placeholder.PlaceholderPair("posX", String.valueOf(posX)),
                    new Placeholder.PlaceholderPair("posY", String.valueOf(posY)),
                    new Placeholder.PlaceholderPair("posZ", String.valueOf(posZ)),
                    new Placeholder.PlaceholderPair("dimension", dimension),
                    new Placeholder.PlaceholderPair("n", "\n")
            );

            player.sendSystemMessage(message);
        }
    }
}
