package com.self.cloudserver;

import com.google.common.collect.Maps;
import com.self.cloudserver.thread.AsyncManager;
import com.self.cloudserver.utils.http.BaseHttpRequest;
import com.self.cloudserver.utils.http.OkHttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

}
