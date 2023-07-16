package com.ezbank.configs;

import com.maxmind.geoip2.DatabaseReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class IpLocationConfig {
    @Value("${ip.location.db.path}")
    private String location;

    @Bean
    public DatabaseReader reader() throws Exception{
        File database = new File(location);
        return new DatabaseReader.Builder(database).build();
    }
}
