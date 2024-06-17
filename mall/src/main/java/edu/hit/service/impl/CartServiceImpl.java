package edu.hit.service.impl;

import com.google.gson.Gson;
import edu.hit.dao.ProductMapper;
import edu.hit.enums.ProductStatusEnum;
import edu.hit.enums.ResponseEnum;
import edu.hit.form.CartAddForm;
import edu.hit.form.CartUpdateForm;
import edu.hit.pojo.Cart;
import edu.hit.pojo.Product;
import edu.hit.service.ICartService;
import edu.hit.vo.CartProductVo;
import edu.hit.vo.CartVo;
import edu.hit.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * @Author Liyifan
 * @Description 购物车Service实现类
 * @Date 15:12 2024/6/13
 **/
@Service
public class CartServiceImpl implements ICartService {

	private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";

	private ProductMapper productMapper;

	private StringRedisTemplate redisTemplate;

	@Autowired
	public CartServiceImpl(ProductMapper productMapper, StringRedisTemplate redisTemplate) {
		this.productMapper = productMapper;
		this.redisTemplate = redisTemplate;
	}

	private Gson gson = new Gson();

	@Override
	public ResponseVo<CartVo> add(Integer uid, CartAddForm form) {
		Integer quantity = 1;
		Product product = productMapper.selectByPrimaryKey(form.getProductId());

		//商品是否存在
		if (product == null) {
			return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
		}

		//商品是否正常在售
		if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
			return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
		}

		//商品库存是否充足
		if (product.getStock() <= 0) {
			return ResponseVo.error(ResponseEnum.PROODUCT_STOCK_ERROR);
		}

		//key: cart_uid
		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);
		Cart cart;
		String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
		if (StringUtils.isEmpty(value)) {
			//没有该商品, 新增
			cart = new Cart(product.getId(), quantity, form.getSelected());
		}else {
			//已经有了，数量+1
			cart = gson.fromJson(value, Cart.class);
			cart.setQuantity(cart.getQuantity() + quantity);
		}
		opsForHash.put(redisKey, String.valueOf(product.getId()), gson.toJson(cart));

		return list(uid);
	}

	@Override
	public ResponseVo<CartVo> list(Integer uid) {
		// 从redis获取用户购物车项
		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);
		Map<String, String> entries = opsForHash.entries(redisKey);
		// 创建 CartVo 对象
		boolean selectAll = true;
		Integer cartTotalQuantity = 0;
		BigDecimal cartTotalPrice = BigDecimal.ZERO;
		CartVo cartVo = new CartVo();
		List<CartProductVo> cartProductVoList = new ArrayList<>();
		for (Map.Entry<String, String> entry : entries.entrySet()) {
			Integer productId = Integer.valueOf(entry.getKey());
			Cart cart = gson.fromJson(entry.getValue(), Cart.class);
			// 创建 CartProductVo 对象
			Product product = productMapper.selectByPrimaryKey(productId);
			if (product != null) {
				CartProductVo cartProductVo = new CartProductVo(productId,
						cart.getQuantity(),
						product.getName(),
						product.getSubtitle(),
						product.getMainImage(),
						product.getPrice(),
						product.getStatus(),
						product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
						product.getStock(),
						cart.getProductSelected()
				);
				cartProductVoList.add(cartProductVo);
				// 若有未选中的则非‘全选’
				if (!cart.getProductSelected()) {
					selectAll = false;
				}

				//计算总价(只计算选中的)
				if (cart.getProductSelected()) {
					cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
				}
			}
			// 计算购物车所有选中商品总价
			cartTotalQuantity += cart.getQuantity();
		}

		cartVo.setSelectedAll(selectAll);
		cartVo.setCartTotalQuantity(cartTotalQuantity);
		cartVo.setCartTotalPrice(cartTotalPrice);
		cartVo.setCartProductVoList(cartProductVoList);
		return ResponseVo.success(cartVo);
	}

	@Override
	public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form) {
		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);

		String value = opsForHash.get(redisKey, String.valueOf(productId));
		if (StringUtils.isEmpty(value)) {
			//没有该商品, 报错
			return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
		}

		//已经有了，修改内容
		Cart cart = gson.fromJson(value, Cart.class);
		if (form.getQuantity() != null && form.getQuantity() >= 0) {
			cart.setQuantity(form.getQuantity());
		}
		if (form.getSelected() != null) {
			cart.setProductSelected(form.getSelected());
		}

		opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));
		return list(uid);
	}

	@Override
	public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);

		String value = opsForHash.get(redisKey, String.valueOf(productId));
		if (StringUtils.isEmpty(value)) {
			//没有该商品, 报错
			return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
		}

		opsForHash.delete(redisKey, String.valueOf(productId));
		return list(uid);
	}

	@Override
	public ResponseVo<CartVo> selectAll(Integer uid) {
		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);
		// 遍历并设定每个商品为选中状态并在redis中更新
		for (Cart cart : listForCart(uid)) {
			cart.setProductSelected(true);
			opsForHash.put(redisKey,
					String.valueOf(cart.getProductId()),
					gson.toJson(cart));
		}

		return list(uid);
	}

	@Override
	public ResponseVo<CartVo> unSelectAll(Integer uid) {
		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);
		// 遍历并设定每个商品为未选中状态并在redis中更新
		for (Cart cart : listForCart(uid)) {
			cart.setProductSelected(false);
			opsForHash.put(redisKey,
					String.valueOf(cart.getProductId()),
					gson.toJson(cart));
		}

		return list(uid);
	}

	@Override
	public ResponseVo<Integer> sum(Integer uid) {
		Integer sum = listForCart(uid).stream()
				.map(Cart::getQuantity)
				.reduce(0, Integer::sum);
		return ResponseVo.success(sum);
	}

	public List<Cart> listForCart(Integer uid) {
		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);
		Map<String, String> entries = opsForHash.entries(redisKey);

		List<Cart> cartList = new ArrayList<>();
		for (Map.Entry<String, String> entry : entries.entrySet()) {
			cartList.add(gson.fromJson(entry.getValue(), Cart.class));
		}

		return cartList;
	}

}
