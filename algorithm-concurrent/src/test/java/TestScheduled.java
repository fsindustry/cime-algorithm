import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import lombok.AllArgsConstructor;

/**
 * Created by fuzhengxin on 2018/3/26.
 */
public class TestScheduled {

    @Test
    public void test1() {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        executorService.scheduleAtFixedRate(new Runner("10秒"), 10, 10, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(new Runner("5秒"), 10, 5, TimeUnit.SECONDS);

        try {
            Thread.sleep(10 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AllArgsConstructor
    private static class Runner implements Runnable {

        private String flag;
        private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public void run() {
            System.out.println(flag + ": run at " + format.format(new Date()));
        }
    }
}
