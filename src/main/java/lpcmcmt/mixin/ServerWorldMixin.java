package lpcmcmt.mixin;

import lpcmcmt.EntityMultiThreadManager;
import lpcmcmt.changes.PathNodeTypeCacheExtra;
import lpcmcmt.mixinInterfaces.IEntityListMixin;
import lpcmcmt.mixinInterfaces.IRWLockable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.PathNodeTypeCache;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.EntityList;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.function.Consumer;

import static lpcmcmt.Utils.MixinStatics.*;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow public abstract @NotNull MinecraftServer getServer();

    @Redirect(method="tick",at= @At(value = "INVOKE", target = "Lnet/minecraft/world/EntityList;forEach(Ljava/util/function/Consumer;)V"))
    void forEach(EntityList instance, Consumer<Entity> action){
        IEntityListMixin inst = getAccessible(instance);
        if (inst.getIterating() != null) {
            throw new UnsupportedOperationException("Only one concurrent iteration supported");
        } else {
            inst.setIterating(inst.getEntities());
            try {
                EntityMultiThreadManager.iterateEntities(getMultiThread(getServer()), inst.getEntities().values(), action);
            } finally {
                inst.setIterating(null);
            }
        }
    }

    @Inject(method = "updateListeners", at = @At("HEAD"))
    void writeHead(CallbackInfo ci){getLock().writeLock().lock();}
    @Inject(method = "updateListeners", at = @At("RETURN"))
    void writeReturn(CallbackInfo ci){getLock().writeLock().unlock();}

    @Inject(method = "getPathNodeTypeCache", at = @At("HEAD"), cancellable = true)
    void getPathNodeTypeCacheRewrite(CallbackInfoReturnable<PathNodeTypeCache> cir){
        cir.setReturnValue(PathNodeTypeCacheExtra.getThreadCache());
        cir.cancel();
    }

    @Unique private ReadWriteLock getLock(){return ((IRWLockable)this).lPC_MCMT$getLock();}

    @Mixin(ServerWorld.ServerEntityHandler.class)
    static class ServerEntityHandlerMixin{
        @Unique private IRWLockable parentLock;
        @Inject(method = "<init>", at = @At("RETURN"))
        void initMixin(ServerWorld serverWorld, CallbackInfo ci){parentLock = (IRWLockable) serverWorld;}

        @Inject(method = {
                "startTracking(Lnet/minecraft/entity/Entity;)V",
                "stopTracking(Lnet/minecraft/entity/Entity;)V"
        }, at = @At("HEAD"))
        void writeHead(CallbackInfo ci){parentLock.lockWrite();}
        @Inject(method = {
                "startTracking(Lnet/minecraft/entity/Entity;)V",
                "stopTracking(Lnet/minecraft/entity/Entity;)V"
        }, at = @At("RETURN"))
        void writeReturn(CallbackInfo ci){parentLock.unlockWrite();}
    }
}
