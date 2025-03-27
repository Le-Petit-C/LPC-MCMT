package lpcmcmt.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.vehicle.DefaultMinecartController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//TODO:ExperimentalMinecartController.moveOnRail

@Mixin(DefaultMinecartController.class)
public abstract class DefaultMinecartControllerMixin {
    //矿车判断是否在铁轨上调用了一次获取blockState
    //然后判断铁轨类型决定移动方式又调用了一次获取blockState
    //多线程处理的话期间blockState有可能发生改变，但是Mojang却直接转换，会出现class cast exception
    //可以尝试将第一次获取到的blockState缓存到下一次使用以解决这个问题，但是有点麻烦而且将判定依赖于调用者感觉不太好
    //我选择在moveOnRail中重新判断一次
    @SuppressWarnings("DiscouragedShift")
    @Inject(method = "moveOnRail",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
                    shift = At.Shift.BY,
                    by = 2
            ),
            cancellable = true
    )
    void moveOnRailExtraTest(CallbackInfo ci, @Local BlockState blockState){
        if(!(blockState.getBlock() instanceof AbstractRailBlock))
            ci.cancel();
    }
}
