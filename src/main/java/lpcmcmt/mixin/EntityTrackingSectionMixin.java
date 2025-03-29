package lpcmcmt.mixin;

import lpcmcmt.mixinInterfaces.IRWLockable;
import net.minecraft.world.entity.EntityTrackingSection;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Mixin(EntityTrackingSection.class)
public class EntityTrackingSectionMixin /*implements IRWLockable*/ {
    /*@Unique ReadWriteLock lock = new ReentrantReadWriteLock();
    @Override public @NotNull ReadWriteLock lPC_MCMT$getLock() {return lock;}*/
/*
    @Inject(method = "forEach*", at = @At("HEAD"))
    void readHead(CallbackInfo ci){lockRead();}
    @Inject(method = "forEach*", at = @At("RETURN"))
    void readReturn(CallbackInfo ci){unlockRead();}
    @Inject(method = "add", at = @At("HEAD"))
    void writeHead(CallbackInfo ci){lockWrite();}
    @Inject(method = "add", at = @At("RETURN"))
    void writeReturn(CallbackInfo ci){unlockWrite();}

    @Inject(method = {
            "remove",
            "swapStatus"
    }, at = @At("HEAD"))
    void returnableWriteHead(CallbackInfoReturnable<?> ci){lockWrite();}
    @Inject(method = {
            "remove",
            "swapStatus"
    }, at = @At("RETURN"))
    void returnableWriteReturn(CallbackInfoReturnable<?> ci){unlockWrite();}*/
}
