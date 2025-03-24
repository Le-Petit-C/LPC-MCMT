package lpcmcmt.mixin;

import lpcmcmt.mixinInterfaces.IEntityMixin;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityChangeListener;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static lpcmcmt.Utils.MixinStatics.*;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntityMixin {
    /*
    @Unique int useCount = 0;

    @Inject(at = @At("HEAD"), method = "setPos")
    void setPosHead(double x, double y, double z, CallbackInfo ci) throws InterruptedException {
        synchronized (this){
            ++useCount;
            if(useCount != 1) this.wait();
        }
    }
    @Inject(at = @At("RETURN"), method = "setPos")
    void setPosReturn(double x, double y, double z, CallbackInfo ci) throws InterruptedException {
        synchronized (this){
            --useCount;
            if(useCount != 0) this.notify();
        }
    }*/
    /*
    @Inject(at = @At("HEAD"), method = "setPos", cancellable = true)
    void setTempPos(double x, double y, double z, CallbackInfo ci){
        if(isInMCMT(getWorld().getServer())){
            tempPos = new Vec3d(x, y, z);
            ci.cancel();
        }
    }
    @Override @Unique public void lPC_MCMT$useTempPos(){
        ((Entity)(Object)this).setPos(tempPos.getX(), tempPos.getY(), tempPos.getZ());
    }*/
}
