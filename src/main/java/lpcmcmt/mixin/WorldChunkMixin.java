package lpcmcmt.mixin;

import lpcmcmt.mixinInterfaces.IRWLockable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Mixin(WorldChunk.class)
public class WorldChunkMixin implements IRWLockable {
    @Shadow @Final World world;
    @Unique private final ReadWriteLock lock = new ReentrantReadWriteLock();
    @Inject(method = {
            "setUnsavedListener",
            "markNeedsSaving"
    }, at = @At("HEAD"))
    void writeHead(CallbackInfo ci){lockWrite();}
    @Inject(method = {
            "setUnsavedListener",
            "markNeedsSaving"
    }, at = @At("RETURN"))
    void writeReturn(CallbackInfo ci){unlockWrite();}

    @Inject(method = {
            "getTickSchedulers",
            "getBlockState",
            "getFluidState(III)Lnet/minecraft/fluid/FluidState;"
    }, at = @At("HEAD"))
    void returnableReadHead(CallbackInfoReturnable<?> cir){lockRead();}
    @Inject(method = {
            "getTickSchedulers",
            "getBlockState",
            "getFluidState(III)Lnet/minecraft/fluid/FluidState;"
    }, at = @At("RETURN"))
    void returnableReadReturn(CallbackInfoReturnable<?> cir){unlockRead();}

    @Inject(method = {
            "setBlockState"
    }, at = @At("HEAD"))
    void returnableWriteHead(CallbackInfoReturnable<?> cir){lockWrite();}
    @Inject(method = {
            "setBlockState"
    }, at = @At("RETURN"))
    void returnableWriteReturn(CallbackInfoReturnable<?> cir){unlockWrite();}

    @Inject(method = {
            "getGameEventDispatcher"
    }, at = @At("HEAD"))
    void getGameEventDispatcherHead(CallbackInfoReturnable<?> cir){
        if(world instanceof ServerWorld) lockWrite();
        else lockRead();
    }
    @Inject(method = {
            "getGameEventDispatcher"
    }, at = @At("RETURN"))
    void getGameEventDispatcherReturn(CallbackInfoReturnable<?> cir){
        if(world instanceof ServerWorld) unlockWrite();
        else unlockRead();
    }

    @Override public @NotNull ReadWriteLock lPC_MCMT$getLock() {return lock;}
    //TODO:createBlockEntity...
}