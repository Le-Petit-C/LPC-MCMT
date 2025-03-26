package lpcmcmt.mixin;

import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static lpcmcmt.Utils.MixinStatics.*;

@Mixin(ServerChunkManager.class)
public class ServerChunkManagerMixin {
    @Final @Shadow Thread serverThread;
    @Final @Shadow private ServerWorld world;
    @Unique boolean using = false;
    @Redirect(
            method = "getChunk(IILnet/minecraft/world/chunk/ChunkStatus;Z)Lnet/minecraft/world/chunk/Chunk;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Thread;currentThread()Ljava/lang/Thread;"
            )
    )
    Thread enableThisMethod(){
        Thread thread = Thread.currentThread();
        if(getMultiThread(world).isExtraThread(thread))
            return serverThread;
        else return thread;
    }
    @Inject(method = "getChunk(IILnet/minecraft/world/chunk/ChunkStatus;Z)Lnet/minecraft/world/chunk/Chunk;",
    at = @At("HEAD"))
    void getChunkHead(CallbackInfoReturnable<Chunk> cir) throws InterruptedException {
        synchronized (this){
            if(using) this.wait();
        }
    }
    @Inject(method = "getChunk(IILnet/minecraft/world/chunk/ChunkStatus;Z)Lnet/minecraft/world/chunk/Chunk;",
            at = @At("RETURN"))
    void getChunkReturn(CallbackInfoReturnable<Chunk> cir){
        synchronized (this){
            using = false;
            this.notify();
        }
    }
}
