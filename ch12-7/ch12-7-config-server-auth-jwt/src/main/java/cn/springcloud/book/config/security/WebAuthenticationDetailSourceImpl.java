package cn.springcloud.book.config.security;

import cn.springcloud.book.config.models.JwtAuthenticationRequest;
import com.google.gson.Gson;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>将传递过来的对象数据封装到JwtAuthenticationRequest里面</p>
 *
 * @author xiaodongsun
 * @date 2019/07/08
 */
@Component
public class WebAuthenticationDetailSourceImpl implements AuthenticationDetailsSource<HttpServletRequest, JwtAuthenticationRequest> {

    @Override
    public JwtAuthenticationRequest buildDetails(HttpServletRequest request) {
        Gson gson = new Gson();
        String json = new String();
        String output = new String();
        BufferedReader br;
        StringBuffer buffer = new StringBuffer(16384);
        JwtAuthenticationRequest jwtAuthenticationRequest = new JwtAuthenticationRequest();
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            while ((output = br.readLine()) != null){
                buffer.append(output);
            }
            json = buffer.toString();
            jwtAuthenticationRequest = gson.fromJson(json, JwtAuthenticationRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jwtAuthenticationRequest;
    }
}
