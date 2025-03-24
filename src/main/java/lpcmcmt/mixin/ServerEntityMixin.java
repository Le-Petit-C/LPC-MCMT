package lpcmcmt.mixin;

import net.minecraft.server.world.ServerEntityManager;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerEntityManager.class)
public abstract class ServerEntityMixin<T extends EntityLike> {
    /*
    @Unique volatile boolean taken = false;
    @Inject(at = @At("HEAD"), method = "addEntity(Lnet/minecraft/world/entity/EntityLike;Z)Z")
    public void addEntityHead(T entity, boolean existing, CallbackInfoReturnable<Boolean> cir) {
        synchronized (this){
            if(taken){
                try {this.wait();}
                catch (InterruptedException ignore) {}
            }
            taken = true;
        }
    }
    @Inject(at = @At("RETURN"), method = "addEntity(Lnet/minecraft/world/entity/EntityLike;Z)Z")
    public void addEntityReturn(T entity, boolean existing, CallbackInfoReturnable<Boolean> cir) {
        synchronized (this){
            taken = false;
            this.notifyAll();
        }
    }
    */
}
