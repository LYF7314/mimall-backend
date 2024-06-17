package edu.hit.service;

import edu.hit.vo.CategoryVo;
import edu.hit.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface ICategoryService {

	ResponseVo<List<CategoryVo>> selectAll();

	void findSubCategoryId(Integer id, Set<Integer> resultSet);

}
