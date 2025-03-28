package lpcmcmt.mixin;

import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@Mixin(ServerChunkManager.class)
public class ServerChunkManagerMixin {
    @Final @Shadow Thread serverThread;
    @Unique ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    @Redirect(
            method = {
                    "getChunk(IILnet/minecraft/world/chunk/ChunkStatus;Z)Lnet/minecraft/world/chunk/Chunk;",
                    "getWorldChunk"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Thread;currentThread()Ljava/lang/Thread;"
            )
    )
    Thread enableThisMethod(){
        //Thread thread = Thread.currentThread();
        //if(getMultiThread(world).isExtraThread(thread))
            return serverThread;
        //else return thread;
    }
    @Inject(method = "getChunk(IILnet/minecraft/world/chunk/ChunkStatus;Z)Lnet/minecraft/world/chunk/Chunk;",
    at = @At("HEAD"))
    void getChunkHead(int x, int z, ChunkStatus leastStatus, boolean create, CallbackInfoReturnable<Chunk> cir){
        if(create) lock.writeLock().lock();
        else lock.readLock().lock();
    }
    @Inject(method = "getWorldChunk",
            at = @At("HEAD"))
    void getWorldChunkHead(CallbackInfoReturnable<Chunk> cir){
        lock.readLock().lock();
    }
    @Inject(method = {
            "getChunk(IILnet/minecraft/world/chunk/ChunkStatus;Z)Lnet/minecraft/world/chunk/Chunk;",
            "getWorldChunk"
    }, at = @At("RETURN"))
    void getChunkReturn(CallbackInfoReturnable<Chunk> cir){
        if(lock.isWriteLockedByCurrentThread())
            lock.writeLock().unlock();
        else lock.readLock().unlock();
    }

    @Inject(method = "putInCache",
            at = @At("HEAD"))
    void putInCacheHead(CallbackInfo ci){
        if(!lock.isWriteLockedByCurrentThread()){
            lock.readLock().unlock();
            lock.writeLock().lock();
        }
    }
}
