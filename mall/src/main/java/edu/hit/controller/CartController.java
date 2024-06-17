package edu.hit.controller;

import edu.hit.consts.MallConst;
import edu.hit.form.CartAddForm;
import edu.hit.form.CartUpdateForm;
import edu.hit.pojo.User;
import edu.hit.service.ICartService;
import edu.hit.vo.CartVo;
import edu.hit.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/*
 * @Author Liyifan
 * @Description 购物车模块
 * @Date 10:24 2024/6/13
 **/
@RestController
public class CartController {

	private ICartService cartService;

	@Autowired
	public CartController(ICartService cartService) {
		this.cartService = cartService;
	}

	/*
	 * @Author Liyifan
	 * @Description 获取购物车所有商品
	 * @Param [session]
	 * @return edu.hit.vo.ResponseVo<edu.hit.vo.CartVo>
	 **/
	@GetMapping("/carts")
	public ResponseVo<CartVo> list(HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.list(user.getId());
	}

	/*
	 * @Author Liyifan
	 * @Description 向购物车增添商品
	 * @Param [cartAddForm, session]
	 * @return edu.hit.vo.ResponseVo<edu.hit.vo.CartVo>
	 **/
	@PostMapping("/carts")
	public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm,
								  HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.add(user.getId(), cartAddForm);
	}

	/*
	 * @Author Liyifan
	 * @Description 根据productId修改购物车内商品（数量，选中状态）
	 * @Param [productId, form, session]
	 * @return edu.hit.vo.ResponseVo<edu.hit.vo.CartVo>
	 **/
	@PutMapping("/carts/{productId}")
	public ResponseVo<CartVo> update(@PathVariable Integer productId,
									 @Valid @RequestBody CartUpdateForm form,
									 HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.update(user.getId(), productId, form);
	}

	/*
	 * @Author Liyifan
	 * @Description 删除购物车内商品
	 * @Param [productId, session]
	 * @return edu.hit.vo.ResponseVo<edu.hit.vo.CartVo>
	 **/
	@DeleteMapping("/carts/{productId}")
	public ResponseVo<CartVo> delete(@PathVariable Integer productId,
									 HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.delete(user.getId(), productId);
	}

	/*
	 * @Author Liyifan
	 * @Description 选择购物车中所有商品
	 * @Param [session]
	 * @return edu.hit.vo.ResponseVo<edu.hit.vo.CartVo>
	 **/
	@PutMapping("/carts/selectAll")
	public ResponseVo<CartVo> selectAll(HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.selectAll(user.getId());
	}

	/*
	 * @Author Liyifan
	 * @Description 取消选择任何购物车中商品
	 * @Param [session]
	 * @return edu.hit.vo.ResponseVo<edu.hit.vo.CartVo>
	 **/
	@PutMapping("/carts/unSelectAll")
	public ResponseVo<CartVo> unSelectAll(HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.unSelectAll(user.getId());
	}

	/*
	 * @Author Liyifan
	 * @Description 计算购物车中所有商品数量的总和
	 * @Param [session]
	 * @return edu.hit.vo.ResponseVo<java.lang.Integer>
	 **/
	@GetMapping("/carts/products/sum")
	public ResponseVo<Integer> sum(HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.sum(user.getId());
	}
}
