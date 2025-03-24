package lpcmcmt.mixin;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lpcmcmt.mixinInterfaces.IEntityListMixin;
import net.minecraft.entity.Entity;
import net.minecraft.world.EntityList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityList.class)
public abstract class EntityListMixin implements IEntityListMixin {
	@Accessor("entities") @Override public abstract Int2ObjectMap<Entity> getEntities();
	@Accessor("entities") @Override public abstract void setEntities(Int2ObjectMap<Entity> entities);
	@Accessor("temp") @Override public abstract Int2ObjectMap<Entity> getTemp();
	@Accessor("temp") @Override public abstract void setTemp(Int2ObjectMap<Entity> temp);
	@Accessor("iterating") @Override public abstract Int2ObjectMap<Entity> getIterating();
	@Accessor("iterating") @Override public abstract void setIterating(Int2ObjectMap<Entity> iterating);
}