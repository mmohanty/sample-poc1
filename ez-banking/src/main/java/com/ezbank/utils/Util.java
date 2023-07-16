package com.ezbank.utils;

import com.ezbank.model.response.Location;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Util {

    static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(0, "JANUARY");
        map.put(1, "FEBRUARY");
        map.put(2, "MARCH");
        map.put(3, "APRIL");
        map.put(4, "MAY");
        map.put(5, "JUNE");
        map.put(6, "JULY");
        map.put(7, "AUGUST");
        map.put(8, "SEPTEMBER");
        map.put(9, "OCTOBER");
        map.put(10, "NOVEMBER");
        map.put(11, "DECEMBER");
    }
    public static String getMonth(int index){
        return map.get(index);
    }

    public static Location getLocation(DatabaseReader reader, String ip){
        try{

            InetAddress ipAddress = InetAddress.getByName(ip);

            CityResponse response = reader.city(ipAddress);

            Country country = response.getCountry();
            System.out.println(country.getName());
            System.out.println(response.getCity().getName());

            return Location.builder()
                    .city(response.getCity().getName())
                    .country(response.getCountry().getName()).build();
        }catch(Exception e){
            log.error("unable to get Location from IP");
        }
        return Location.builder()
                .city("Hyderabad")
                .country("India").build();
    }
}
