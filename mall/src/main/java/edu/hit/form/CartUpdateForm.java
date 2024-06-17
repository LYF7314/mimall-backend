package edu.hit.form;

import lombok.Data;

/*
 * @Author Liyifan
 * @Description 购物车更新表单
 **/
@Data
public class CartUpdateForm {

	private Integer quantity;

	private Boolean selected;
}
