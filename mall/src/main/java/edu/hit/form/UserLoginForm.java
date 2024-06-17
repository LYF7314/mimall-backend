package edu.hit.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author ReubenRogar
 * @Description 用户登录表单
 */
@Data
public class UserLoginForm {

	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
