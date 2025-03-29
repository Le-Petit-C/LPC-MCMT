package lpcmcmt.Utils;

import it.unimi.dsi.fastutil.longs.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.SortedSet;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.Supplier;
import java.util.stream.LongStream;

public class ThreadLocalLongSortedSet implements LongSortedSet {
    ThreadLocal<LongSortedSet> set;
    public ThreadLocalLongSortedSet(Supplier<? extends LongSortedSet> supplier){set = ThreadLocal.withInitial(supplier);}
    @Override public LongBidirectionalIterator iterator(long fromElement) {return set.get().iterator(fromElement);}
    @Override public int size() {return set.get().size();}
    @Override public boolean isEmpty() {return set.get().isEmpty();}
    @Override public @NotNull LongBidirectionalIterator iterator() {return set.get().iterator();}
    @Override public LongIterator longIterator() {return set.get().longIterator();}
    @Override public LongSpliterator spliterator() {return set.get().spliterator();}
    @Override public void addFirst(Long aLong) {set.get().addFirst(aLong);}
    @Override public void addLast(Long aLong) {set.get().addLast(aLong);}
    @Override public Long getFirst() {return set.get().getFirst();}
    @Override public Long getLast() {return set.get().getLast();}
    @Override public Long removeFirst() {return set.get().removeFirst();}
    @Override public Long removeLast() {return set.get().removeLast();}
    @Override public SortedSet<Long> reversed() {return set.get().reversed();}
    @Override public LongSpliterator longSpliterator() {return set.get().longSpliterator();}
    @Override public void forEach(LongConsumer action) {set.get().forEach(action);}
    @Override public void forEach(it.unimi.dsi.fastutil.longs.LongConsumer action) {set.get().forEach(action);}
    @Override public @NotNull Object @NotNull [] toArray() {return set.get().toArray();}
    @Override public @NotNull <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {return set.get().toArray(a);}
    @Override public <T> T[] toArray(IntFunction<T[]> generator) {return set.get().toArray(generator);}
    @Override public boolean containsAll(@NotNull Collection<?> c) {return set.get().containsAll(c);}
    @Override public boolean addAll(@NotNull Collection<? extends Long> c) {return set.get().addAll(c);}
    @Override public boolean removeAll(@NotNull Collection<?> c) {return set.get().removeAll(c);}
    @Override public boolean retainAll(@NotNull Collection<?> c) {return set.get().retainAll(c);}
    @Override public void clear() {set.remove();}
    @Override public boolean add(long key) {return set.get().add(key);}
    @Override public boolean contains(long key) {return set.get().contains(key);}
    @Override public long[] toLongArray() {return set.get().toLongArray();}
    @Override public long[] toArray(long[] a) {return set.get().toArray(a);}
    @Override public boolean addAll(LongCollection c) {return set.get().addAll(c);}
    @Override public boolean containsAll(LongCollection c) {return set.get().containsAll(c);}
    @Override public boolean removeAll(LongCollection c) {return set.get().removeAll(c);}
    @Override public boolean removeIf(LongPredicate filter) {return set.get().removeIf(filter);}
    @Override public boolean removeIf(it.unimi.dsi.fastutil.longs.LongPredicate filter) {return set.get().removeIf(filter);}
    @Override public boolean retainAll(LongCollection c) {return set.get().retainAll(c);}
    @Override public LongStream longStream() {return set.get().longStream();}
    @Override public LongStream longParallelStream() {return set.get().longParallelStream();}
    @Override public boolean remove(long k) {return set.get().remove(k);}
    @Override public LongSortedSet subSet(long fromElement, long toElement) {return set.get().subSet(fromElement, toElement);}
    @Override public LongSortedSet headSet(long toElement) {return set.get().headSet(toElement);}
    @Override public LongSortedSet tailSet(long fromElement) {return set.get().tailSet(fromElement);}
    @Override public LongComparator comparator() {return set.get().comparator();}
    @Override public long firstLong() {return set.get().firstLong();}
    @Override public long lastLong() {return set.get().lastLong();}
}
