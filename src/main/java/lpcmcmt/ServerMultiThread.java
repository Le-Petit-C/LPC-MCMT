package lpcmcmt;

import lpcmcmt.Utils.IntegerLock;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

public class ServerMultiThread {
    public final @NotNull MinecraftServer server;
    public ServerMultiThread(@NotNull MinecraftServer server, int extraThreadCount){
        enabled = true;
        this.extraThreadCount = extraThreadCount;
        this.server = server;
        runLock.add(extraThreadCount + 1);
        for(int a = 0; a < extraThreadCount; ++a){
            Thread thread = new Thread(this::subThreads);
            thread.start();
        }
    }
    public ServerMultiThread(@NotNull MinecraftServer server){
        this(server, 1);
    }
    public void disable(){
        enabled = false;
        multiThreadRun(()->{});
    }
    public void multiThreadRun(@NotNull Runnable runnable){
        this.runnable = runnable;
        runRunnable(true);
    }

    private final int extraThreadCount;
    private final @NotNull IntegerLock runLock = new IntegerLock();
    private final @NotNull IntegerLock stopLock = new IntegerLock();
    private Runnable runnable;
    private boolean enabled;
    private boolean runRunnable(boolean isCallerThread){
        if(isCallerThread) stopLock.add(extraThreadCount + 1);
        try {runLock.subtractAndWaitUntilZero();}
        catch (InterruptedException ignore) {}
        runnable.run();
        if(isCallerThread) runLock.add(extraThreadCount + 1);
        try {stopLock.subtractAndWaitUntilZero();}
        catch (InterruptedException ignore) {}
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
        }
    }
}
