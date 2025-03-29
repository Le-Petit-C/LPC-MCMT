package lpcmcmt.Utils;

import it.unimi.dsi.fastutil.bytes.Byte2LongFunction;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectFunction;
import it.unimi.dsi.fastutil.chars.Char2LongFunction;
import it.unimi.dsi.fastutil.chars.Char2ObjectFunction;
import it.unimi.dsi.fastutil.doubles.Double2LongFunction;
import it.unimi.dsi.fastutil.doubles.Double2ObjectFunction;
import it.unimi.dsi.fastutil.floats.Float2LongFunction;
import it.unimi.dsi.fastutil.floats.Float2ObjectFunction;
import it.unimi.dsi.fastutil.ints.Int2LongFunction;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.objects.*;
import it.unimi.dsi.fastutil.shorts.Short2LongFunction;
import it.unimi.dsi.fastutil.shorts.Short2ObjectFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.*;

public class ThreadLocalLong2ObjectMap<V> implements Long2ObjectMap<V> {
    private final ThreadLocal<Long2ObjectMap<V>> map;
    public ThreadLocalLong2ObjectMap(Supplier<? extends Long2ObjectMap<V>> supplier) {map = ThreadLocal.withInitial(supplier);}
    @Override public V apply(Long key) {return map.get().apply(key);}
    @Override public @NotNull <V1> Function<Long, V1> andThen(@NotNull Function<? super V, ? extends V1> after) {return map.get().andThen(after);}
    @Override public int size() {return map.get().size();}
    @Override public void clear() {map.remove();}
    @Override public boolean isEmpty() {return map.get().isEmpty();}
    @Override public boolean containsValue(Object value) {return map.get().containsValue(value);}
    @Override public void putAll(@NotNull Map<? extends Long, ? extends V> m) {map.get().putAll(m);}
    @Override public void defaultReturnValue(V rv) {map.get().defaultReturnValue(rv);}
    @Override public V defaultReturnValue() {return map.get().defaultReturnValue();}
    @Override public Long2ByteFunction andThenByte(Object2ByteFunction<V> after) {return map.get().andThenByte(after);}
    @Override public Byte2ObjectFunction<V> composeByte(Byte2LongFunction before) {return map.get().composeByte(before);}
    @Override public Long2ShortFunction andThenShort(Object2ShortFunction<V> after) {return map.get().andThenShort(after);}
    @Override public Short2ObjectFunction<V> composeShort(Short2LongFunction before) {return map.get().composeShort(before);}
    @Override public Long2IntFunction andThenInt(Object2IntFunction<V> after) {return map.get().andThenInt(after);}
    @Override public Int2ObjectFunction<V> composeInt(Int2LongFunction before) {return map.get().composeInt(before);}
    @Override public Long2LongFunction andThenLong(Object2LongFunction<V> after) {return map.get().andThenLong(after);}
    @Override public Long2ObjectFunction<V> composeLong(Long2LongFunction before) {return map.get().composeLong(before);}
    @Override public Long2CharFunction andThenChar(Object2CharFunction<V> after) {return map.get().andThenChar(after);}
    @Override public Char2ObjectFunction<V> composeChar(Char2LongFunction before) {return map.get().composeChar(before);}
    @Override public Long2FloatFunction andThenFloat(Object2FloatFunction<V> after) {return map.get().andThenFloat(after);}
    @Override public Float2ObjectFunction<V> composeFloat(Float2LongFunction before) {return map.get().composeFloat(before);}
    @Override public Long2DoubleFunction andThenDouble(Object2DoubleFunction<V> after) {return map.get().andThenDouble(after);}
    @Override public Double2ObjectFunction<V> composeDouble(Double2LongFunction before) {return map.get().composeDouble(before);}
    @Override public <T> Long2ObjectFunction<T> andThenObject(Object2ObjectFunction<? super V, ? extends T> after) {return map.get().andThenObject(after);}
    @Override public <T> Object2ObjectFunction<T, V> composeObject(Object2LongFunction<? super T> before) {return map.get().composeObject(before);}
    @Override public <T> Long2ReferenceFunction<T> andThenReference(Object2ReferenceFunction<? super V, ? extends T> after) {return map.get().andThenReference(after);}
    @Override public <T> Reference2ObjectFunction<T, V> composeReference(Reference2LongFunction<? super T> before) {return map.get().composeReference(before);}
    @Override public ObjectSet<Entry<V>> long2ObjectEntrySet() {return map.get().long2ObjectEntrySet();}
    @Override public @NotNull LongSet keySet() {return map.get().keySet();}
    @Override public @NotNull ObjectCollection<V> values() {return map.get().values();}
    @Override public V apply(long operand) {return map.get().apply(operand);}
    @Override public V put(long key, V value) {return map.get().put(key, value);}
    @Override public V get(long key) {return map.get().get(key);}
    @Override public boolean containsKey(long key) {return map.get().containsKey(key);}
    @Override public void forEach(BiConsumer<? super Long, ? super V> consumer) {map.get().forEach(consumer);}
    @Override public void replaceAll(BiFunction<? super Long, ? super V, ? extends V> function) {map.get().replaceAll(function);}
    @Override public @Nullable V putIfAbsent(Long key, V value) {return map.get().putIfAbsent(key, value);}
    @Override public boolean remove(Object key, Object value) {return map.get().remove(key, value);}
    @Override public boolean replace(Long key, V oldValue, V newValue) {return map.get().replace(key, oldValue, newValue);}
    @Override public @Nullable V replace(Long key, V value) {return map.get().replace(key, value);}
    @Override public V computeIfAbsent(Long key, @NotNull Function<? super Long, ? extends V> mappingFunction) {return map.get().computeIfAbsent(key, mappingFunction);}
    @Override public V computeIfPresent(Long key, @NotNull BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {return map.get().computeIfPresent(key, remappingFunction);}
    @Override public V compute(Long key, @NotNull BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {return map.get().compute(key, remappingFunction);}
    @Override public V merge(Long key, @NotNull V value, @NotNull BiFunction<? super V, ? super V, ? extends V> remappingFunction) {return map.get().merge(key, value, remappingFunction);}
    @Override public V getOrDefault(long key, V defaultValue) {return map.get().getOrDefault(key, defaultValue);}
    @Override public V remove(long key) {return map.get().remove(key);}
    @Override public V putIfAbsent(long key, V value) {return map.get().putIfAbsent(key, value);}
    @Override public boolean remove(long key, Object value) {return map.get().remove(key, value);}
    @Override public boolean replace(long key, V oldValue, V newValue) {return map.get().replace(key, oldValue, newValue);}
    @Override public V replace(long key, V value) {return map.get().replace(key, value);}
    @Override public V computeIfAbsent(long key, LongFunction<? extends V> mappingFunction) {return map.get().computeIfAbsent(key, mappingFunction);}
    @Override public V computeIfAbsent(long key, Long2ObjectFunction<? extends V> mappingFunction) {return map.get().computeIfAbsent(key, mappingFunction);}
    @Override public V computeIfPresent(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {return map.get().computeIfPresent(key, remappingFunction);}
    @Override public V compute(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {return map.get().compute(key, remappingFunction);}
    @Override public V merge(long key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {return map.get().merge(key, value, remappingFunction);}
}
