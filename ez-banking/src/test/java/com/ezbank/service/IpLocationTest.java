package com.ezbank.service;

import com.ezbank.model.response.ip.IPLocation;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class IpLocationTest {

    @Test
    public void test_ipLocation_parsing() throws Exception{
        String str = IOUtils.toString(this.getClass().getResourceAsStream("/sample/iplocation-sample-test.json"),
                "UTF-8");
        Gson gson = new Gson();
        IPLocation ipLocation = gson.fromJson(str, IPLocation.class);
        System.out.println(ipLocation);
    }
}
