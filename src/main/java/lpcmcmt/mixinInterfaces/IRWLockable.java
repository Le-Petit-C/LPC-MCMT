package lpcmcmt.mixinInterfaces;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.locks.ReadWriteLock;

public interface IRWLockable {
    @NotNull ReadWriteLock lPC_MCMT$getLock();
    default void lockRead(){lPC_MCMT$getLock().readLock().lock();}
    default void unlockRead(){lPC_MCMT$getLock().readLock().unlock();}
    default void lockWrite(){lPC_MCMT$getLock().writeLock().lock();}
    default void unlockWrite(){lPC_MCMT$getLock().writeLock().unlock();}
}
