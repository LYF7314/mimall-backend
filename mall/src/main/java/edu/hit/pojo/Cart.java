package edu.hit.pojo;

import lombok.Data;

/*
 * @Author ReubenRogar
 * @Description 购物车单条商品
 **/
@Data
public class Cart {

	private Integer productId;

	private Integer quantity;

	private Boolean productSelected;

	public Cart() {
	}

	public Cart(Integer productId, Integer quantity, Boolean productSelected) {
		this.productId = productId;
		this.quantity = quantity;
		this.productSelected = productSelected;
	}
}
