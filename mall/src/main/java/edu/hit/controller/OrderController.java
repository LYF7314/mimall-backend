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
	@PostMapping("/orders")
	public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm form,
									  HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return orderService.create(user.getId(), form.getShippingId());
	}

	/*
	 * @Author Liyifan
	 * @Description 获取订单
	 * @Param [pageNum, pageSize, session]
	 * @return edu.hit.vo.ResponseVo<com.github.pagehelper.PageInfo>
	 **/
	@GetMapping("/orders")
	public ResponseVo<PageInfo> list(@RequestParam Integer pageNum,
									 @RequestParam Integer pageSize,
									 HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return orderService.list(user.getId(), pageNum, pageSize);
	}

	@GetMapping("/orders/{orderNo}")
	public ResponseVo<OrderVo> detail(@PathVariable Long orderNo,
									  HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return orderService.detail(user.getId(), orderNo);
	}

	@PutMapping("/orders/{orderNo}")
	public ResponseVo cancel(@PathVariable Long orderNo,
							 HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return orderService.cancel(user.getId(), orderNo);
	}

}
