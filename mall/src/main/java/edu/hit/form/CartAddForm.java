package edu.hit.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/*
 * @Author Liyifan
 * @Description 购物车添加商品表单
 **/
@Data
public class CartAddForm {

	@NotNull
	private Integer productId;

	private Boolean selected = true;
}
