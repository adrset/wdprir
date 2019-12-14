package ovh.ara.Lab1.threads;

import java.util.concurrent.CountDownLatch;

public interface ILatchable {
    public void setLatch(CountDownLatch c);
}
