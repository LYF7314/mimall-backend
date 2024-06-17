package edu.hit.controller;

import edu.hit.service.ICategoryService;
import edu.hit.vo.CategoryVo;
import edu.hit.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @Author Liyifan
 * @Description 商品种类相关业务
 * @Date 16:38 2024/6/13
 **/
@RestController
public class CategoryController {

	private ICategoryService categoryService;

	@Autowired
	public CategoryController(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/*
	 * @Author Liyifan
	 * @Description
	 * @Param []
	 * @return edu.hit.vo.ResponseVo<java.util.List<edu.hit.vo.CategoryVo>>
	 **/
	@GetMapping("/categories")
	public ResponseVo<List<CategoryVo>> selectAll() {
		return categoryService.selectAll();
	}
}
