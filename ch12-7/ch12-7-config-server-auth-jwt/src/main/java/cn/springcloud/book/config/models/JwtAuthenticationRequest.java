package cn.springcloud.book.config.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/07/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtAuthenticationRequest implements Serializable {
    private static final long serialVersionUID = -1926354273373697416L;

    private String username;

    private String password;
}
