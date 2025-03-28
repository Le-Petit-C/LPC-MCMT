package lpcmcmt.mixin;

import lpcmcmt.mixinInterfaces.IRWLockable;
import net.minecraft.server.world.ServerEntityManager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Mixin(ServerEntityManager.class)
public class ServerEntityManagerMixin implements IRWLockable {
    @Unique ReadWriteLock lock = new ReentrantReadWriteLock();
    @Override public @NotNull ReadWriteLock lPC_MCMT$getLock() {return lock;}

    @Inject(method = {
            "save"
    }, at = @At("HEAD"))
    void readHead(CallbackInfo ci){lockRead();}
    @Inject(method = {
            "save"
    }, at = @At("RETURN"))
    void readReturn(CallbackInfo ci){unlockRead();}

    @Inject(method = {
            "updateTrackingStatus(Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/entity/EntityTrackingStatus;)V",
            "tick",
            "flush"
    }, at = @At("HEAD"))
    void writeHead(CallbackInfo ci){lockWrite();}
    @Inject(method = {
            "updateTrackingStatus(Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/entity/EntityTrackingStatus;)V",
            "tick",
            "flush"
    }, at = @At("RETURN"))
    void writeReturn(CallbackInfo ci){unlockWrite();}

    @Inject(method = {
            "has",
            "shouldTick*",
            "isLoaded"
    }, at = @At("HEAD"))
    void returnableReadHead(CallbackInfoReturnable<?> ci){lockRead();}
    @Inject(method = {
            "has",
            "shouldTick*",
            "isLoaded"
    }, at = @At("RETURN"))
    void returnableReadReturn(CallbackInfoReturnable<?> ci){unlockRead();}

    @Inject(method = {
            "addEntity(Lnet/minecraft/world/entity/EntityLike;Z)Z"
    }, at = @At("HEAD"))
    void returnableWriteHead(CallbackInfoReturnable<?> ci){lockWrite();}
    @Inject(method = {
            "addEntity(Lnet/minecraft/world/entity/EntityLike;Z)Z"
    }, at = @At("RETURN"))
    void returnableWriteReturn(CallbackInfoReturnable<?> ci){unlockWrite();}
}
