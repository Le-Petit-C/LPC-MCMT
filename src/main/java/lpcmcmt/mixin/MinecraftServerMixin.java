package lpcmcmt.mixin;

import lpcmcmt.ServerMultiThread;
import lpcmcmt.mixinInterfaces.IMinecraftServerMixin;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements IMinecraftServerMixin {
    @Unique @NotNull public ServerMultiThread multiThreadManager
            = new ServerMultiThread((MinecraftServer)(Object)this);
    @Override @Unique public ServerMultiThread lPC_MCMT$getMultiThreadManager(){
        return multiThreadManager;
    }
}
