package cn.springcloud.book.config.jwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/07/08
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
public class LoginRequest {

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;
}
