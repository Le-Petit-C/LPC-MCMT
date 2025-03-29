package lpcmcmt.mixin;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongAVLTreeSet;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import lpcmcmt.Utils.ThreadLocalLong2ObjectMap;
import lpcmcmt.Utils.ThreadLocalLongSortedSet;
import net.minecraft.world.entity.EntityLike;
import net.minecraft.world.entity.EntityTrackingSection;
import net.minecraft.world.entity.SectionedEntityCache;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Mixin(SectionedEntityCache.class)
public class SectionedEntityCacheMixin<T extends EntityLike> {
    /*@Unique private final ReadWriteLock lock = new ReentrantReadWriteLock();
    @Inject(method = {
            "forEachInBox"
    }, at = @At("HEAD"))
    void readHead(CallbackInfo ci){lock.readLock().lock();}
    @Inject(method = {
            "forEachInBox"
    }, at = @At("RETURN"))
    void readReturn(CallbackInfo ci){lock.readLock().unlock();}
    @Inject(method = {
            "removeSection",
    }, at = @At("HEAD"))
    void writeHead(CallbackInfo ci){lock.writeLock().lock();}
    @Inject(method = {
            "removeSection"
    }, at = @At("RETURN"))
    void writeReturn(CallbackInfo ci){lock.writeLock().unlock();}

    @Inject(method = {
            "getSections(J)Ljava/util/stream/LongStream;",
            "getTrackingSections",
            "findTrackingSection",
            "getChunkPositions"
    }, at = @At("HEAD"))
    void returnableReadHead(CallbackInfoReturnable<?> cir){lock.readLock().lock();}
    @Inject(method = {
            "getSections(J)Ljava/util/stream/LongStream;",
            "getTrackingSections",
            "findTrackingSection",
            "getChunkPositions"
    }, at = @At("RETURN"))
    void returnableReadReturn(CallbackInfoReturnable<?> cir){lock.readLock().unlock();}

    @Inject(method = {
            "getTrackingSection",
    }, at = @At("HEAD"))
    void returnableWriteHead(CallbackInfoReturnable<?> cir){lock.writeLock().lock();}
    @Inject(method = {
            "getTrackingSection"
    }, at = @At("RETURN"))
    void returnableWriteReturn(CallbackInfoReturnable<?> cir){lock.writeLock().unlock();}*/
    @Final @Shadow @Mutable private LongSortedSet trackedPositions;
    @Final @Shadow @Mutable private Long2ObjectMap<EntityTrackingSection<T>> trackingSections;
    @Inject(method = "<init>", at = @At("RETURN"))
    void initReturn(CallbackInfo ci){
        trackedPositions = new ThreadLocalLongSortedSet(LongAVLTreeSet::new);
        trackingSections = new ThreadLocalLong2ObjectMap<>(Long2ObjectOpenHashMap::new);
    }
}
