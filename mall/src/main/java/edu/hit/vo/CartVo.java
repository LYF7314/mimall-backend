package edu.hit.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/*
 * @Author Liyifan
 * @Description 购物车列表 Value Object
 **/
@Data
public class CartVo {

	private List<CartProductVo> cartProductVoList;

	private Boolean selectedAll;

	private BigDecimal cartTotalPrice;

	private Integer cartTotalQuantity;
}
