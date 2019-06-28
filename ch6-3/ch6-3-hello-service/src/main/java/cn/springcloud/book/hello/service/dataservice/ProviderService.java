package cn.springcloud.book.hello.service.dataservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/06/20
 */
@FeignClient(name = "sc-provider-service", fallback = ProviderServiceFallback.class)
public interface ProviderService {

    @GetMapping("/getDashboard")
    List<String> getProviderData();
}
