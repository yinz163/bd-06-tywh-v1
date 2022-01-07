package com.hnxt.security.security;

import com.hnxt.common.util.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *  * <p>
 *  * t密码的处理方法类型
 *  * </p>
 * @Author yinz
 * @Date 2021/10/21 - 10:31
 */
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

	public DefaultPasswordEncoder() {
		this(-1);
	}

	/**
	 * @param strength
	 *            the log rounds to use, between 4 and 31
	 */
	public DefaultPasswordEncoder(int strength) {

	}

	public String encode(CharSequence rawPassword) {
		return MD5.encrypt(rawPassword.toString());
	}

	/**
	 *
	 * @param rawPassword  前端传输的密码
	 * @param encodedPassword 数据库查询出的密码
	 * @return
	 */
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
	}
}
