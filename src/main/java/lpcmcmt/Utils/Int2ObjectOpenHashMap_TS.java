package lpcmcmt.Utils;

import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

//Thread-safe extend
/*
    Int2ObjectOpenHashMap的线程安全版本
    为了线程安全牺牲了部分性能，性能下降最严重的部分估计是迭代器
    因为Java没有即刻的析构函数，迭代器没用之后无法立即销毁释放，只能在创建迭代器时拷贝一份原内容的副本
    不过如果调用forEach函数会好一些，有明确的释放时间
*/
@SuppressWarnings("unused")
public class Int2ObjectOpenHashMap_TS<V> extends Int2ObjectOpenHashMap<V> {
    //TODO:ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public Int2ObjectOpenHashMap_TS(final int expected, final float f){super(expected, f);}
    public Int2ObjectOpenHashMap_TS(final int expected){super(expected);}
    public Int2ObjectOpenHashMap_TS(){}
    public Int2ObjectOpenHashMap_TS(final Map<? extends Integer, ? extends V> m, final float f){super(m, f);}
    public Int2ObjectOpenHashMap_TS(final Map<? extends Integer, ? extends V> m){super(m);}
    public Int2ObjectOpenHashMap_TS(final Int2ObjectMap<V> m, final float f){super(m, f);}
    public Int2ObjectOpenHashMap_TS(final Int2ObjectMap<V> m) {super(m);}
    public Int2ObjectOpenHashMap_TS(final int[] k, final V[] v, final float f){super(k, v, f);}
    public Int2ObjectOpenHashMap_TS(final int[] k, final V[] v){super(k, v);}
    @Override public void ensureCapacity(final int capacity){synchronized (this){super.ensureCapacity(capacity);}}
    @Override public void putAll(Map<? extends Integer, ? extends V> m){synchronized (this){super.putAll(m);}}
    @Override public V put(final int k, final V v){synchronized (this){return super.put(k, v);}}
    //protected method shiftKeys is final, maybe override that is unnecessary
    @Override public V remove(final int k){synchronized (this){return super.remove(k);}}
    @Override public V get(final int k){synchronized (this){return super.get(k);}}
    @Override public boolean containsKey(final int k){synchronized (this){return super.containsKey(k);}}
    @Override public boolean containsValue(final Object v){synchronized (this){return super.containsValue(v);}}
    @Override public V getOrDefault(final int k, final V defaultValue){synchronized (this){return super.getOrDefault(k, defaultValue);}}
    @Override public V putIfAbsent(final int k, final V v){synchronized (this){return super.putIfAbsent(k, v);}}
    @Override public boolean remove(final int k, final Object v){synchronized (this){return super.remove(k, v);}}
    @Override public boolean replace(final int k, final V oldValue, final V v){synchronized (this){return super.replace(k, oldValue, v);}}
    @Override public V replace(final int k, final V v){synchronized (this){return super.replace(k, v);}}
    @Override public V computeIfAbsent(final int k, final java.util.function.IntFunction<? extends V> mappingFunction){synchronized (this){return super.computeIfAbsent(k, mappingFunction);}}
    @Override public V computeIfAbsent(final int key, final Int2ObjectFunction<? extends V> mappingFunction){synchronized (this){return super.computeIfAbsent(key, mappingFunction);}}
    @Override public V computeIfPresent(final int k, final java.util.function.BiFunction<? super Integer, ? super V, ? extends V> remappingFunction){synchronized (this){return super.computeIfPresent(k, remappingFunction);}}
    @Override public V compute(final int k, final java.util.function.BiFunction<? super Integer, ? super V, ? extends V> remappingFunction){synchronized (this){return super.compute(k, remappingFunction);}}
    @Override public V merge(final int k, final V v, final java.util.function.BiFunction<? super V, ? super V, ? extends V> remappingFunction){synchronized (this){return super.merge(k, v, remappingFunction);}}
    @Override public void clear(){synchronized (this){super.clear();}}
    @Override public FastEntrySet<V> int2ObjectEntrySet(){return new EntrySet<>(this);}
    @Override public @NotNull IntSet keySet(){return new KeySet(this);}
    @Override public @NotNull ObjectCollection<V> values(){return new ValueSet<>(this);}

    private FastEntrySet<V> superInt2ObjectEntrySet(){return super.int2ObjectEntrySet();}
    private IntSet superKeySet(){return super.keySet();}
    private ObjectCollection<V> superValues(){return super.values();}
    private record EntrySet<V>(Int2ObjectOpenHashMap_TS<V> parent) implements FastEntrySet<V>{
        @Override public ObjectIterator<Entry<V>> fastIterator() {return new EntryIterator<>(parent);}
        @Override public int size() {return parent.size();}
        @Override public boolean isEmpty() {return parent.isEmpty();}
        @Override public boolean contains(Object o) {synchronized (this){return parent.superInt2ObjectEntrySet().contains(o);}}
        @Override public @NotNull ObjectIterator<Entry<V>> iterator() {return new EntryIterator<>(parent);}
        @Override public @NotNull Object @NotNull [] toArray() {synchronized (parent){return parent.superInt2ObjectEntrySet().toArray();}}
        @Override public @NotNull <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {synchronized (parent){return parent.superInt2ObjectEntrySet().toArray(a);}}
        @Override public boolean add(Entry<V> vEntry) {synchronized (parent){return parent.superInt2ObjectEntrySet().add(vEntry);}}
        @Override public boolean remove(Object o) {synchronized (parent){return parent.superInt2ObjectEntrySet().remove(o);}}
        @Override public boolean containsAll(@NotNull Collection<?> c) {synchronized (parent){return parent.superInt2ObjectEntrySet().containsAll(c);}}
        @Override public boolean addAll(@NotNull Collection<? extends Entry<V>> c) {synchronized (parent){return parent.superInt2ObjectEntrySet().addAll(c);}}
        @Override public boolean removeAll(@NotNull Collection<?> c) {synchronized (parent){return parent.superInt2ObjectEntrySet().removeAll(c);}}
        @Override public boolean retainAll(@NotNull Collection<?> c) {synchronized (parent){return parent.superInt2ObjectEntrySet().retainAll(c);}}
        @Override public void clear() {synchronized (parent){parent.superInt2ObjectEntrySet().clear();}}
        @Override public void forEach(Consumer<? super Entry<V>> action) {synchronized (parent){parent.superInt2ObjectEntrySet().forEach(action);}}
        @Override public void fastForEach(Consumer<? super Entry<V>> consumer){synchronized (parent){parent.superInt2ObjectEntrySet().fastForEach(consumer);}}
    }
    private static class EntryIterator<V> implements ObjectIterator<Entry<V>>{
        final Int2ObjectOpenHashMap_TS<V> parent;
        Int2ObjectOpenHashMap<V> iterateMap;
        ObjectIterator<Int2ObjectMap.Entry<V>> iterator;
        int lastKey = 0;
        EntryIterator(Int2ObjectOpenHashMap_TS<V> parent){
            this.parent = parent;
            synchronized (this.parent){
                iterateMap = new Int2ObjectOpenHashMap<>();
                parent.superInt2ObjectEntrySet().forEach(key->iterateMap.put(key.getIntKey(), key.getValue()));
            }
            iterator = iterateMap.int2ObjectEntrySet().fastIterator();
        }
        @Override public boolean hasNext() {return iterator.hasNext();}
        @Override public Entry<V> next() {
            Entry<V> entry = iterator.next();
            lastKey = entry.getIntKey();
            return entry;
        }
        @Override public void remove(){iterator.remove();parent.remove(lastKey);}
    }
    private record KeySet(Int2ObjectOpenHashMap_TS<?> parent) implements IntSet{
        @Override public int size() {return parent.size();}
        @Override public boolean isEmpty() {return parent.isEmpty();}
        @Override public @NotNull IntIterator iterator() {return new KeyIterator(parent);}
        @Override public @NotNull Object @NotNull [] toArray() {synchronized (parent){return parent.keySet().toArray();}}
        @Override public @NotNull <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {synchronized (parent){return parent.keySet().toArray(a);}}
        @Override public boolean containsAll(@NotNull Collection<?> c) {synchronized (parent){return parent.keySet().containsAll(c);}}
        @Override public boolean addAll(@NotNull Collection<? extends Integer> c) {synchronized (parent){return parent.keySet().addAll(c);}}
        @Override public boolean removeAll(@NotNull Collection<?> c) {synchronized (parent){return parent.keySet().removeAll(c);}}
        @Override public boolean retainAll(@NotNull Collection<?> c) {synchronized (parent){return parent.keySet().retainAll(c);}}
        @Override public void clear() {synchronized (parent){parent.keySet().clear();}}
        @Override public boolean add(int key) {synchronized (parent){return parent.keySet().add(key);}}
        @Override public boolean contains(int key) {synchronized (parent){return parent.keySet().contains(key);}}
        @Override public int[] toIntArray() {synchronized (parent){return parent.keySet().toIntArray();}}
        @Override public int[] toArray(int[] a) {synchronized (parent){return parent.keySet().toArray(a);}}
        @Override public boolean addAll(IntCollection c) {synchronized (parent){return parent.keySet().addAll(c);}}
        @Override public boolean containsAll(IntCollection c) {synchronized (parent){return parent.keySet().containsAll(c);}}
        @Override public boolean removeAll(IntCollection c) {synchronized (parent){return parent.keySet().removeAll(c);}}
        @Override public boolean retainAll(IntCollection c) {synchronized (parent){return parent.keySet().retainAll(c);}}
        @Override public boolean remove(int k) {synchronized (parent){return parent.keySet().remove(k);}}
        @Override public void forEach(IntConsumer action){synchronized (parent){parent.superKeySet().forEach(action);}}
    }
    private static class KeyIterator implements IntIterator{
        final Int2ObjectOpenHashMap_TS<?> parent;
        IntOpenHashSet iterateSet;
        IntIterator iterator;
        int lastKey = 0;
        KeyIterator(Int2ObjectOpenHashMap_TS<?> parent) {
            this.parent = parent;
            synchronized (this.parent){iterateSet = new IntOpenHashSet(parent.superKeySet());}
            iterator = iterateSet.iterator();
        }
        @Override public int nextInt() {return lastKey = iterator.nextInt();}
        @Override public boolean hasNext() {return iterator.hasNext();}
        @Override public void remove(){iterator.remove();parent.remove(lastKey);}
    }
    private record ValueSet<V>(Int2ObjectOpenHashMap_TS<V> parent) implements ObjectCollection<V>{
        @Override public int size() {return parent.size();}
        @Override public boolean isEmpty() {return parent.isEmpty();}
        @Override public boolean contains(Object o) {return parent.superValues().contains(o);}
        @Override public @NotNull ObjectIterator<V> iterator() {return new ValueIterator<>(parent);}
        @Override public @NotNull Object @NotNull [] toArray() {synchronized (this){return parent.superValues().toArray();}}
        @Override public @NotNull <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {synchronized (this){return parent.superValues().toArray(a);}}
        @Override public boolean add(V v) {synchronized (this){return parent.superValues().add(v);}}
        @Override public boolean remove(Object o) {synchronized (this){return parent.superValues().remove(o);}}
        @Override public boolean containsAll(@NotNull Collection<?> c) {synchronized (this){return parent.superValues().containsAll(c);}}
        @Override public boolean addAll(@NotNull Collection<? extends V> c) {synchronized (this){return parent.superValues().addAll(c);}}
        @Override public boolean removeAll(@NotNull Collection<?> c) {synchronized (this){return parent.superValues().removeAll(c);}}
        @Override public boolean retainAll(@NotNull Collection<?> c) {synchronized (this){return parent.superValues().retainAll(c);}}
        @Override public void clear() {synchronized (this){parent.superValues().clear();}}
    }
    private static class ValueIterator<V> implements ObjectIterator<V>{
        EntryIterator<V> entryIterator;
        ValueIterator(Int2ObjectOpenHashMap_TS<V> parent) {entryIterator = new EntryIterator<>(parent);}
        @Override public V next() {return entryIterator.next().getValue();}
        @Override public boolean hasNext() {return entryIterator.hasNext();}
        @Override public void remove(){entryIterator.remove();}
    }
}
