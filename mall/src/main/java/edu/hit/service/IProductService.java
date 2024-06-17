package edu.hit.service;

import com.github.pagehelper.PageInfo;
import edu.hit.vo.ProductDetailVo;
import edu.hit.vo.ResponseVo;

public interface IProductService {

	ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

	ResponseVo<ProductDetailVo> detail(Integer productId);
}
