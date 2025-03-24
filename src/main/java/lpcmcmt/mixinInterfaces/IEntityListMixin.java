package lpcmcmt.mixinInterfaces;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;

@SuppressWarnings("unused")
public interface IEntityListMixin {
    Int2ObjectMap<Entity> getEntities();
    void setEntities(Int2ObjectMap<Entity> entities);
    Int2ObjectMap<Entity> getTemp();
    void setTemp(Int2ObjectMap<Entity> temp);
    Int2ObjectMap<Entity> getIterating();
    void setIterating(Int2ObjectMap<Entity> iterating);
}
