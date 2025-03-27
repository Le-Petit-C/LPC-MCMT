package lpcmcmt.Utils;

public class IntegerLock {
    public void addAndWaitUntilZero(int delta) throws InterruptedException {
        synchronized (this){
            n += delta;
            if(n == 0)
                notifyAll();
            else this.wait();
        }
    }
    public void add(int delta){
        synchronized (this){
            n += delta;
            if(n == 0) this.notifyAll();
        }
    }
    public void subtractAndWaitUntilZero() throws InterruptedException {addAndWaitUntilZero(-1);}
    public void subtract(){add(-1);}
    public void add(){add(1);}

    private int n = 0;
}
