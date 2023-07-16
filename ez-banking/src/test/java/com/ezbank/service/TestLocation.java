package com.ezbank.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.InetAddress;

public class TestLocation {

    @Test
    public void test() throws Exception{
        File database = new File("/Users/manasmohanty/Personal/Workspace/poc-whitepaper/GeoLite2-City_20230714/GeoLite2-City.mmdb");

// This reader object should be reused across lookups as creation of it is
// expensive.
        DatabaseReader reader = new DatabaseReader.Builder(database).build();

// If you want to use caching at the cost of a small (~2MB) memory overhead:
// new DatabaseReader.Builder(file).withCache(new CHMCache()).build();

        InetAddress ipAddress = InetAddress.getByName("43.204.25.55");

        CityResponse response = reader.city(ipAddress);

        Country country = response.getCountry();
        System.out.println(country.getName());
        System.out.println(response.getCity().getName());
    }
}
