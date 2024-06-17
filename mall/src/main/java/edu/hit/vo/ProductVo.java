package edu.hit.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Liyifan
 * @Description 商品值对象
 */
@Data
public class ProductVo {

	private Integer id;

	private Integer categoryId;

	private String name;

	private String subtitle;

	private String mainImage;

	private Integer status;

	private BigDecimal price;
}
