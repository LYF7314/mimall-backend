package edu.hit.vo;

import lombok.Data;

import java.math.BigDecimal;

/*
 * @Author Liyifan
 * @Description 购物车列表一条记录的值对象
 * @Date 10:47 2024/6/13
 **/
@Data
public class CartProductVo {

	private Integer productId;

	// 购买数量
	private Integer quantity;

	private String productName;

	private String productSubtitle;

	private String productMainImage;

	private BigDecimal productPrice;

	private Integer productStatus;

	// quantity * productPrice
	private BigDecimal productTotalPrice;

	private Integer productStock;

	// 商品是否选中
	private Boolean productSelected;

	public CartProductVo(Integer productId, Integer quantity, String productName, String productSubtitle, String productMainImage, BigDecimal productPrice, Integer productStatus, BigDecimal productTotalPrice, Integer productStock, Boolean productSelected) {
		this.productId = productId;
		this.quantity = quantity;
		this.productName = productName;
		this.productSubtitle = productSubtitle;
		this.productMainImage = productMainImage;
		this.productPrice = productPrice;
		this.productStatus = productStatus;
		this.productTotalPrice = productTotalPrice;
		this.productStock = productStock;
		this.productSelected = productSelected;
	}
}
