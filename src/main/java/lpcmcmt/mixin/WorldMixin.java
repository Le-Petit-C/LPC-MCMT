package lpcmcmt.mixin;

import lpcmcmt.Utils.ThreadLocalRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class WorldMixin {
    @Final @Mutable @Shadow public Random random;
    @Inject(method = "<init>", at = @At("RETURN"))
    void initReturn(CallbackInfo ci){
        random = new ThreadLocalRandom();
    }
}
