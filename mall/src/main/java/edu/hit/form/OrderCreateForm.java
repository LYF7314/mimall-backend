package edu.hit.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/*
 * @Author Liyifan
 * @Description 订单创建表达
 **/
@Data
public class OrderCreateForm {

	@NotNull
	private Integer shippingId;
}
