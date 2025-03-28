package lpcmcmt.mixin;

import lpcmcmt.Utils.ThreadLocalRandom;
import lpcmcmt.mixinInterfaces.IRWLockable;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Mixin(World.class)
public class WorldMixin implements IRWLockable {
    @Final @Mutable @Shadow public Random random;
    @Inject(method = "<init>", at = @At("RETURN"))
    void initReturn(CallbackInfo ci){
        random = new ThreadLocalRandom();
    }
    @Unique ReadWriteLock lock = new ReentrantReadWriteLock();
    @Override public @NotNull ReadWriteLock lPC_MCMT$getLock(){return lock;}

    @Inject(method = {
            "getBlockState"
    }, at = @At("HEAD"))
    void returnableReadHead(CallbackInfoReturnable<?> cir){lockRead();}
    @Inject(method = {
            "getBlockState"
    }, at = @At("RETURN"))
    void returnableReadReturn(CallbackInfoReturnable<?> cir){unlockRead();}

    @Inject(method = {
            "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z"
    }, at = @At("HEAD"))
    void returnableWriteHead(CallbackInfoReturnable<?> cir){lockWrite();}
    @Inject(method = {
            "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z"
    }, at = @At("RETURN"))
    void returnableWriteReturn(CallbackInfoReturnable<?> cir){unlockWrite();}
}
