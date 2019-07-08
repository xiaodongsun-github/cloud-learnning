package cn.springcloud.book.config.jwt.config;

import cn.springcloud.book.config.jwt.dto.LoginRequest;
import cn.springcloud.book.config.jwt.dto.Token;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/07/08
 */
@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
public class ConfigClientBootstrapConfiguration {

    private static Log logger = LogFactory.getLog(ConfigClientBootstrapConfiguration.class);

    @Value("${spring.cloud.config.username}")
    private String jwtUserName;
    @Value("${spring.cloud.config.password}")
    private String jwtPassword;
    @Value("${spring.cloud.config.endpoint}")
    private String jwtEndpoint;

    private String jwtToken;

    @Autowired
    private ConfigurableEnvironment environment;

    @PostConstruct
    public void init(){
        RestTemplate restTemplate = new RestTemplate();
        LoginRequest loginBackend = new LoginRequest();
        loginBackend.setUsername(jwtUserName);
        loginBackend.setPassword(jwtPassword);
        String serviceUrl = jwtEndpoint;

        Token token;

        try {
            token = restTemplate.postForObject(serviceUrl, loginBackend, Token.class);
            if (token.getToken() == null){
                throw  new Exception();
            }
            //设置token
            setJwtToken(token.getToken());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Bean
    public ConfigServicePropertySourceLocator configServicePropertySourceLocator(ConfigClientProperties configClientProperties){
        ConfigServicePropertySourceLocator configServicePropertySourceLocator = new ConfigServicePropertySourceLocator(configClientProperties);
        configServicePropertySourceLocator.setRestTemplate(customRestTemplate());
        return configServicePropertySourceLocator;
    }

    @Bean
    public ConfigClientProperties configClientProperties(){
        ConfigClientProperties clientProperties = new ConfigClientProperties(this.environment);
        clientProperties.setEnabled(true);
        return clientProperties;
    }

    private RestTemplate customRestTemplate(){
        Map<String, String> headers = new HashMap<>();
        headers.put("token", "Bearer:" + jwtToken);
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout((60 * 1000 * 3) + 5000);
        RestTemplate template = new RestTemplate(requestFactory);
        if (!headers.isEmpty()){
            template.setInterceptors(Arrays.<ClientHttpRequestInterceptor> asList(new GenericRequestHeaderInterceptor(headers)));
        }
        return template;
    }

    public static class GenericRequestHeaderInterceptor implements ClientHttpRequestInterceptor{

        private final Map<String, String> headers;

        public GenericRequestHeaderInterceptor(Map<String, String> headers) {
            this.headers = headers;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
            headers.entrySet().stream().forEach(header -> {
                httpRequest.getHeaders().add(header.getKey(), header.getValue());
            });
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        }
    }
}
