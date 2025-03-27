package lpcmcmt;

import lpcmcmt.Utils.IntegerLock;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.crash.CrashException;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class ServerMultiThread {
    public final @NotNull MinecraftServer server;
    public boolean isExtraThread(Thread thread){return extraThreads.contains(thread);}
    public ServerMultiThread(@NotNull MinecraftServer server, int extraThreadCount){
        enabled = true;
        this.server = server;
        extraThreads = new HashSet<>();
        runLock.add(extraThreadCount + 1);
        for(int a = 0; a < extraThreadCount; ++a){
            Thread thread = new Thread(this::subThreads);
            extraThreads.add(thread);
            thread.start();
        }
    }
    public ServerMultiThread(@NotNull MinecraftServer server){
        this(server, 15);
    }
    public void disable(){
        enabled = false;
        multiThreadRun(()->{});
    }
    public void multiThreadRun(@NotNull Runnable runnable){
        this.runnable = runnable;
        runRunnable(true);
    }

    private CrashException exception;
    private final HashSet<Thread> extraThreads;
    private final @NotNull IntegerLock runLock = new IntegerLock();
    private final @NotNull IntegerLock stopLock = new IntegerLock();
    private Runnable runnable;
    private boolean enabled;
    private boolean runRunnable(boolean isCallerThread){
        stopLock.add();
        try {runLock.subtractAndWaitUntilZero();}
        catch (InterruptedException ignore) {}
        runnable.run();
        runLock.add();
        try {stopLock.subtractAndWaitUntilZero();}
        catch (InterruptedException ignore) {}
        if(isCallerThread && exception != null){
            CrashException e = exception;
            exception = null;
            throw e;
        }
        return false;
    }
    private void subThreads(){
        try{
            while(enabled){
                if(runRunnable(false))
                    break;
            }
        } catch (Throwable exception){
            Main.LOGGER.error(exception.toString());
            if(exception instanceof CrashException crashException){
                this.exception = crashException;
                stopLock.subtract();
            }
        }
    }
}
