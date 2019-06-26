package cn.springcloud.book.hello.service.dataservice;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>feign中的断路器 降级处理</p>
 *
 * @author xiaodongsun
 * @date 2019/6/20
 */
@Component
public class ProviderServiceFallback implements ProviderService {

    @Override
    public List<String> getProviderData() {
        String fallback = "provider service fallback";
        List<String> result = new ArrayList<>();
        result.add(fallback);
        return result;
    }
}
