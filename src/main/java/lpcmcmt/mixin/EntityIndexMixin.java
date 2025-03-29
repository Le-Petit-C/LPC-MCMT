package lpcmcmt.mixin;

import lpcmcmt.mixinInterfaces.IRWLockable;
import net.minecraft.world.entity.EntityIndex;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Mixin(EntityIndex.class)
public class EntityIndexMixin implements IRWLockable {
    @Unique final ReadWriteLock lock = new ReentrantReadWriteLock();
    @Override public @NotNull ReadWriteLock lPC_MCMT$getLock() {return lock;}

    @Inject(method = {
            "forEach",
            "get*"
    }, at = @At("HEAD"))
    void readHead(CallbackInfo ci){lockRead();}
    @Inject(method = {
            "forEach",
            "get*"
    }, at = @At("RETURN"))
    void readReturn(CallbackInfo ci){unlockRead();}

    @Inject(method = {
            "add",
            "remove"
    }, at = @At("HEAD"))
    void writeHead(CallbackInfo ci){lockWrite();}
    @Inject(method = {
            "add",
            "remove"
    }, at = @At("RETURN"))
    void writeReturn(CallbackInfo ci){unlockWrite();}

}
