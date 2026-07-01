package ru.logitechno.mixin;


import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.logitechno.Pomidor;

@Mixin(LightBlock.class)
public abstract class LightBlockMixin extends BlockBehaviourMixin {
    @Inject(
            method = "getShape",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getShape(CallbackInfoReturnable<VoxelShape> cir) {
        if (Pomidor.CONFIG.isLightBlockVisible()) {
            cir.setReturnValue(Shapes.block());
        }
    }

    @Inject(
            method = "getRenderShape",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getRenderType(CallbackInfoReturnable<RenderShape> cir) {
        if (Pomidor.CONFIG.isLightBlockVisible()) {
            cir.setReturnValue(RenderShape.MODEL);
        }
    }
}
