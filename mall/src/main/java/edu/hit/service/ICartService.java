package edu.hit.service;

import edu.hit.form.CartAddForm;
import edu.hit.form.CartUpdateForm;
import edu.hit.pojo.Cart;
import edu.hit.vo.CartVo;
import edu.hit.vo.ResponseVo;

import java.util.List;

public interface ICartService {

	ResponseVo<CartVo> add(Integer uid, CartAddForm form);

	ResponseVo<CartVo> list(Integer uid);

	ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form);

	ResponseVo<CartVo> delete(Integer uid, Integer productId);

	ResponseVo<CartVo> selectAll(Integer uid);

	ResponseVo<CartVo> unSelectAll(Integer uid);

	ResponseVo<Integer> sum(Integer uid);

	List<Cart> listForCart(Integer uid);
}
