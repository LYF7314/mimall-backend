package edu.hit.controller;

import com.github.pagehelper.PageInfo;
import edu.hit.consts.MallConst;
import edu.hit.form.OrderCreateForm;
import edu.hit.pojo.User;
import edu.hit.service.IOrderService;
import edu.hit.vo.OrderVo;
import edu.hit.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/*
 * @Author Liyifan
 * @Description 商品订单相关业务
 **/
@RestController
public class OrderController {

	private IOrderService orderService;

	@Autowired
	public OrderController(IOrderService orderService) {
		this.orderService = orderService;
	}

	/*
	 * @Author Liyifan
	 * @Description 创建订单
	 * @Param [form, session]
	 * @return edu.hit.vo.ResponseVo<edu.hit.vo.OrderVo>
	 **/
	// TODO
	@PostMapping("/orders")
	public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm form,
									  HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return orderService.create(user.getId(), form.getShippingId());
	}

}
