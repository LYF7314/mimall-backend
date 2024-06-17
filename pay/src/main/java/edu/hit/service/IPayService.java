package edu.hit.service;

import edu.hit.pojo.PayInfo;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * Created by 廖师兄
 */
public interface IPayService {

	/**
	 * 创建/发起支付
	 */
	PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

	/**
	 * 异步通知处理
	 */
	String asyncNotify(int orderId);

	/**
	 * 查询支付记录(通过订单号)
	 * @param orderId
	 * @return
	 */
	PayInfo queryByOrderId(String orderId);
}
