package lpcmcmt.mixin;

import lpcmcmt.EntityMultiThreadManager;
import lpcmcmt.mixinInterfaces.IEntityListMixin;
import lpcmcmt.mixinInterfaces.IEntityMixin;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.EntityList;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;

import static lpcmcmt.Utils.MixinStatics.*;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow public abstract @NotNull MinecraftServer getServer();

    @Redirect(method="tick",at= @At(value = "INVOKE", target = "Lnet/minecraft/world/EntityList;forEach(Ljava/util/function/Consumer;)V"))
    void forEach(EntityList instance, Consumer<Entity> action){
        IEntityListMixin inst = getAccessible(instance);
        if (inst.getIterating() != null) {
            throw new UnsupportedOperationException("Only one concurrent iteration supported");
        } else {
            inst.setIterating(inst.getEntities());
            try {
                EntityMultiThreadManager.iterateEntities(getMultiThread(getServer()), inst.getEntities().values(), action);
                //getMultiThread(getServer()).multiThreadIterate(inst.getEntities().values(), action);
                /*for(Entity entity : inst.getEntities().values())
                    ((IEntityMixin)entity).lPC_MCMT$useTempPos();*/
                /*for (Entity entity : inst.getEntities().values()) {
                    action.accept(entity);
                }*/
            } finally {
                inst.setIterating(null);
            }
        }
    }
}
