package lpcmcmt.Utils;

import lpcmcmt.ServerMultiThread;
import lpcmcmt.mixinInterfaces.IEntityListMixin;
import lpcmcmt.mixinInterfaces.IMinecraftServerMixin;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.EntityList;

@SuppressWarnings("unused")
public class MixinStatics {
    public static ServerMultiThread getMultiThread(MinecraftServer server){
        return ((IMinecraftServerMixin)server).lPC_MCMT$getMultiThreadManager();
    }
    public static ServerMultiThread getMultiThread(Entity entity){
        return getMultiThread(entity.getServer());
    }
    public static ServerMultiThread getMultiThread(ServerWorld world){
        return getMultiThread(world.getServer());
    }
    public static IEntityListMixin getAccessible(EntityList entityList){
        return (IEntityListMixin)entityList;
    }
    public static boolean isInMCMT(MinecraftServer server){
        return getMultiThread(server).isInMCMT();
    }
}
