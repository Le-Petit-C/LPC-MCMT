package lpcmcmt.mixin;

import lpcmcmt.mixinInterfaces.IRWLockable;
import net.minecraft.world.chunk.light.LevelPropagator;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Mixin(LevelPropagator.class)
public class LevelPropagatorMixin implements IRWLockable {
    @Unique ReadWriteLock lock = new ReentrantReadWriteLock();
    @Override public @NotNull ReadWriteLock lPC_MCMT$getLock() {return lock;}

    @Inject(method = {
            "removePendingUpdate",
            "removePendingUpdateIf",
            "updateLevel(JJIZ)V",
            "propagateLevel(JJIZ)V"
    }, at = @At("HEAD"))
    void writeHead(CallbackInfo cir){lockWrite();}
    @Inject(method = {
            "removePendingUpdate",
            "removePendingUpdateIf",
            "updateLevel(JJIZ)V",
            "propagateLevel(JJIZ)V"
    }, at = @At("RETURN"))
    void writeReturn(CallbackInfo cir){unlockWrite();}

    @Inject(method = "applyPendingUpdates", at = @At("HEAD"))
    void returnableWriteHead(CallbackInfoReturnable<?> cir){lockWrite();}
    @Inject(method = "applyPendingUpdates", at = @At("RETURN"))
    void returnableWriteReturn(CallbackInfoReturnable<?> cir){unlockWrite();}
}
