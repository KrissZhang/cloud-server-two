package com.self.cloudserver;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.self.cloudserver.thread.AsyncManager;
import com.self.cloudserver.utils.http.BaseHttpRequest;
import com.self.cloudserver.utils.http.OkHttpUtil;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    @Test
    public void testOkHttp(){
        String url = "https://wis.qq.com/weather/common";
        Map<String, Object> reqMap = Maps.newHashMap();
        reqMap.put("source", "pc");
        reqMap.put("weather_type", "forecast_24h");
        reqMap.put("province", "重庆市");
        reqMap.put("city", "重庆市");
        reqMap.put("county", "渝北区");

        BaseHttpRequest request = new BaseHttpRequest(url, BaseHttpRequest.Method.GET);
        request.getQueryParams().putAll(reqMap);
        String response = OkHttpUtil.getInstance().sendRequest(request);

        System.out.println(response);
    }

    @Autowired
    private RedissonClient redissonClient;

    private static Integer count = 0;

    @Test
    public void testRedisson(){
        List<Integer> list = Collections.synchronizedList(Lists.newArrayList());
        for (int i = 0; i < 100; i++) {
            list.add((i + 1));
        }

        list.parallelStream().forEach(integer -> {
            RLock rLock = redissonClient.getLock("lock:prefix:" + "redisson");
            try {
                //获取锁失败时阻塞等待
                rLock.lock();

                //业务逻辑
                count += integer;
                System.out.println("count:" + count);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }finally {
                //需要解锁的key被锁定且被当前线程持有时才能解锁
                if(rLock.isLocked() && rLock.isHeldByCurrentThread()){
                    rLock.unlock();
                }
            }
        });
    }

}
