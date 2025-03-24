package lpcmcmt.Utils;

import lpcmcmt.ServerMultiThread;
import lpcmcmt.mixinInterfaces.IEntityListMixin;
import lpcmcmt.mixinInterfaces.IMinecraftServerMixin;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EntityList;

public class MixinStatics {
    public static ServerMultiThread getMultiThread(MinecraftServer server){
        return ((IMinecraftServerMixin)server).lPC_MCMT$getMultiThreadManager();
    }
    public static IEntityListMixin getAccessible(EntityList entityList){
        return (IEntityListMixin)entityList;
    }
}
