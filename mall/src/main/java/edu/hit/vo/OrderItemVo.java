package edu.hit.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Liyifan
 * @Description 订单列表一条记录的值对象
 */
@Data
public class OrderItemVo {

	private Long orderNo;

	private Integer productId;

	private String productName;

	private String productImage;

	private BigDecimal currentUnitPrice;

	private Integer quantity;

	private BigDecimal totalPrice;

	private Date createTime;
}
