package cn.springcloud.book.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/06/20
 */
@RestController
public class ProviderController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/getDashboard")
    public List<String> getProviderData(){
        List<String> provider = new ArrayList<>();
        provider.add("hystrix dashboard");
        return provider;
    }
}
