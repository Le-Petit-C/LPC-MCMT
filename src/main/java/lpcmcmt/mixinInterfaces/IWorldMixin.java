package lpcmcmt.mixinInterfaces;

import java.util.concurrent.locks.ReadWriteLock;

public interface IWorldMixin {
    ReadWriteLock entityLock();
    ReadWriteLock blockLock();
}
