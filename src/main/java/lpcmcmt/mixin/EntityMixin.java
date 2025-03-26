package lpcmcmt.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Unique boolean using = false;
    @Inject(method = "setPos", at=@At("HEAD"))
    void setPosHead(CallbackInfo ci) throws InterruptedException {
        synchronized (this){
            if(using) this.wait();
        }
    }
    @Inject(method = "setPos", at=@At("RETURN"))
    void setPosReturn(CallbackInfo ci){
        synchronized (this){
            using = false;
            this.notify();
        }
    }
}
