package lpcmcmt.changes;

import net.minecraft.entity.ai.pathing.PathNodeTypeCache;

public class PathNodeTypeCacheExtra {
    private static final ThreadLocal<PathNodeTypeCache> threadLocalCache
            = ThreadLocal.withInitial(PathNodeTypeCache::new);
    public static PathNodeTypeCache getThreadCache(){
        return threadLocalCache.get();
    }
}
