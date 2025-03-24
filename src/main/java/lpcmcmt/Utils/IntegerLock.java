package lpcmcmt.Utils;

import lpcmcmt.Main;

@SuppressWarnings("unused")
public class IntegerLock {
    static int _id = 0;
    int id = ++_id;
    public int getCurrentInteger(){return n;}
    public void waitUntilZero() throws InterruptedException {
        synchronized (this){
            if(n == 0) return;
            this.wait();
        }
    }
    public void addAndWaitUntilZero(int delta) throws InterruptedException {
        synchronized (this){
            n += delta;
            Main.LOGGER.info("Lock{} - current{}", id, n);
            if(n == 0){
                Main.LOGGER.info("Lock{} - notify", id);
                notifyAll();
            }
            else {
                Main.LOGGER.info("Lock{} - wait", id);
                this.wait();
            }
        }
    }
    public void add(int delta){
        synchronized (this){
            n += delta;
            if(n == 0)
                this.notifyAll();
        }
    }
    private int n = 0;
    public void add(){add(1);}
    public void subtract(){add(-1);}
    public void addAndWaitUntilZero() throws InterruptedException {addAndWaitUntilZero(1);}
    public void subtractAndWaitUntilZero() throws InterruptedException {addAndWaitUntilZero(-1);}
}
