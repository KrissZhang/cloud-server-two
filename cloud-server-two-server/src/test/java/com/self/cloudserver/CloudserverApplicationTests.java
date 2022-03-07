package com.self.cloudserver;

import com.self.cloudserver.thread.AsyncManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.TimerTask;

@SpringBootTest
class CloudserverApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testAsyncManager(){
        long startMs = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            AsyncManager.threadPool().execute(new TimerTask() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
        }

        long endMs = System.currentTimeMillis();

        System.out.println("消耗了" + (endMs - startMs) + "毫秒");
    }

}
