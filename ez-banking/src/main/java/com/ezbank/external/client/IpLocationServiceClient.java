package com.ezbank.external.client;

import com.ezbank.model.response.ip.IPLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ipLocation", url = "https://apiip.net/api/check")
public interface IpLocationServiceClient {


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    IPLocation getLocation(@RequestParam(value="ip") String ip,
                           @RequestParam(value="accessKey") String accessKey);

}
