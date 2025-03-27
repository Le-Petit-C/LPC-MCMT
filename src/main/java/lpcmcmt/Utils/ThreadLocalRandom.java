package lpcmcmt.Utils;

import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSplitter;

public class ThreadLocalRandom implements Random {
    ThreadLocal<Random> threadLocalRandom = ThreadLocal.withInitial(Random::create);
    @Override public Random split() {return threadLocalRandom.get().split();}
    @Override public RandomSplitter nextSplitter() {return threadLocalRandom.get().nextSplitter();}
    @Override public void setSeed(long seed) {threadLocalRandom.get().setSeed(seed);}
    @Override public int nextInt() {return threadLocalRandom.get().nextInt();}
    @Override public int nextInt(int bound) {return threadLocalRandom.get().nextInt(bound);}
    @Override public long nextLong() {return threadLocalRandom.get().nextLong();}
    @Override public boolean nextBoolean() {return threadLocalRandom.get().nextBoolean();}
    @Override public float nextFloat() {return threadLocalRandom.get().nextFloat();}
    @Override public double nextDouble() {return threadLocalRandom.get().nextDouble();}
    @Override public double nextGaussian() {return threadLocalRandom.get().nextGaussian();}
}
