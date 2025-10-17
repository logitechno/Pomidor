package ru.logitechno.mixin;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.LightBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.logitechno.Pomidor;

@Mixin(LightBlock.class)
public abstract class LightBlockMixin extends AbstractBlockMixin {
    @Inject(
            method = "getOutlineShape",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getOutlineShape(CallbackInfoReturnable<VoxelShape> cir) {
        if (Pomidor.CONFIG.isLightBlockVisible()) {
            cir.setReturnValue(VoxelShapes.fullCube());
        }
    }

    @Inject(
            method = "getRenderType",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getRenderType(BlockState state, CallbackInfoReturnable<BlockRenderType> cir) {
        if (Pomidor.CONFIG.isLightBlockVisible()) {
            cir.setReturnValue(BlockRenderType.MODEL);
        }
    }

    public void getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (Pomidor.CONFIG.isLightBlockVisible()) {
            cir.setReturnValue(VoxelShapes.empty());
        }
    }
}
