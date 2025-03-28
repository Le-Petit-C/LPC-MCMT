package lpcmcmt.mixin;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lpcmcmt.mixinInterfaces.IEntityListMixin;
import lpcmcmt.mixinInterfaces.IRWLockable;
import net.minecraft.entity.Entity;
import net.minecraft.world.EntityList;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Mixin(EntityList.class)
public abstract class EntityListMixin implements IEntityListMixin, IRWLockable {
	@Accessor("entities") @Override public abstract Int2ObjectMap<Entity> getEntities();
	@Accessor("entities") @Override public abstract void setEntities(Int2ObjectMap<Entity> entities);
	@Accessor("temp") @Override public abstract Int2ObjectMap<Entity> getTemp();
	@Accessor("temp") @Override public abstract void setTemp(Int2ObjectMap<Entity> temp);
	@Accessor("iterating") @Override public abstract Int2ObjectMap<Entity> getIterating();
	@Accessor("iterating") @Override public abstract void setIterating(Int2ObjectMap<Entity> iterating);
	@Unique private final ReadWriteLock lock = new ReentrantReadWriteLock();
	@Override public @NotNull ReadWriteLock lPC_MCMT$getLock(){return lock;}

	@Inject(method = {
			"add",
			"remove"
	}, at = @At("HEAD"))
	void writeHead(CallbackInfo ci){lockWrite();}
	@Inject(method = {
			"add",
			"remove"
	}, at = @At("RETURN"))
	void writeReturn(CallbackInfo ci){unlockWrite();}

	@Inject(method = "forEach", at = @At("HEAD"))
	void readHead(CallbackInfo ci){lockRead();}
	@Inject(method = "forEach", at = @At("RETURN"))
	void readReturn(CallbackInfo ci){unlockRead();}

	@Inject(method = "has", at = @At("HEAD"))
	void returnableReadHead(CallbackInfoReturnable<?> ci){lockRead();}
	@Inject(method = "has", at = @At("RETURN"))
	void returnableReadReturn(CallbackInfoReturnable<?> ci){unlockRead();}
}