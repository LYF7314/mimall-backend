package edu.hit.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Liyifan
 * @Description 商品详情值对象
 */
@Data
public class ProductDetailVo {

	private Integer id;

	private Integer categoryId;

	private String name;

	private String subtitle;

	private String mainImage;

	private String subImages;

	private String detail;

	private BigDecimal price;

	private Integer stock;

	private Integer status;

	private Date createTime;

	private Date updateTime;
}
