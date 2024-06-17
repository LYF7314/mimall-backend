package edu.hit.service;

import com.github.pagehelper.PageInfo;
import edu.hit.vo.OrderVo;
import edu.hit.vo.ResponseVo;


public interface IOrderService {

	ResponseVo<OrderVo> create(Integer id, Integer shippingId);
}
