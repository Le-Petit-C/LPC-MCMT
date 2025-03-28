package lpcmcmt.mixin;

//import lpcmcmt.mixinInterfaces.IRWLockable;
//import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.PathNodeTypeCache;
//import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Unique;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;

@Mixin(PathNodeTypeCache.class)
public class PathNodeTypeCacheMixin/* implements IRWLockable */{
    /*@Unique ReadWriteLock lock = new ReentrantReadWriteLock();
    @Override public @NotNull ReadWriteLock lPC_MCMT$getLock() {return lock;}

    @Inject(method = "add", at = @At("HEAD"))
    void returnableWriteHead(CallbackInfoReturnable<PathNodeType> cir){lockWrite();}
    @Inject(method = "add", at = @At("RETURN"))
    void returnableWriteReturn(CallbackInfoReturnable<PathNodeType> cir){unlockWrite();}
    @Inject(method = "invalidate", at = @At("HEAD"))
    void writeHead(CallbackInfo ci){lockWrite();}
    @Inject(method = "invalidate", at = @At("RETURN"))
    void writeReturn(CallbackInfo ci){unlockWrite();}*/
}
