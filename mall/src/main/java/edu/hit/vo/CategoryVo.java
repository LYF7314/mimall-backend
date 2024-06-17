package edu.hit.vo;

import lombok.Data;

import java.util.List;

/*
 * @Author Liyifan
 * @Description 商品种类值对象
 **/
@Data
public class CategoryVo {

	private Integer id;

	private Integer parentId;

	private String name;

	private Integer sortOrder;

	private List<CategoryVo> subCategories;
}
