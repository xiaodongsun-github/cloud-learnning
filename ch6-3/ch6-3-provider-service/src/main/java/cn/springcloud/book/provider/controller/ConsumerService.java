package cn.springcloud.book.provider.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/06/20
 */
@FeignClient(name = "sc-hello-service")
public interface ConsumerService {

    @GetMapping(value = "/helloService")
    String getHelloServiceData();
}
