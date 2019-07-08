package cn.springcloud.book.config.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/07/08
 */
@Getter
@AllArgsConstructor
public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 5369182567875005074L;

    private final String token;

}
