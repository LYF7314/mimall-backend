package edu.hit.service.impl;

import edu.hit.service.IOrderService;
import edu.hit.vo.OrderVo;
import edu.hit.vo.ResponseVo;
import org.springframework.stereotype.Service;



@Service
public class OrderServiceImpl implements IOrderService {

	@Override
	public ResponseVo<OrderVo> create(Integer id, Integer shippingId) {
		return null;
	}
}
