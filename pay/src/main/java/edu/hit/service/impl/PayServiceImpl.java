package edu.hit.service.impl;

import com.google.gson.Gson;
import edu.hit.dao.PayInfoMapper;
import edu.hit.enums.PayPlatformEnum;
import edu.hit.pojo.PayInfo;
import edu.hit.service.IPayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by 廖师兄
 */
@Slf4j
@Service
public class PayServiceImpl implements IPayService {

	private final static String QUEUE_PAY_NOTIFY = "payNotify";

	@Autowired
	private BestPayService bestPayService;

	@Autowired
	private PayInfoMapper payInfoMapper;

	@Autowired
	private AmqpTemplate amqpTemplate;

	/**
	 * 创建/发起支付
	 *
	 * @param orderId
	 * @param amount
	 */
	@Override
	public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum) {
		//写入数据库
		PayInfo payInfo = new PayInfo(Long.parseLong(orderId),
				PayPlatformEnum.getByBestPayTypeEnum(bestPayTypeEnum).getCode(),
				OrderStatusEnum.NOTPAY.name(),
				amount);
		payInfoMapper.insertSelective(payInfo);

//		PayRequest request = new PayRequest();
//		request.setOrderName("4559066-最好的支付sdk");
//		request.setOrderId(orderId);
//		request.setOrderAmount(amount.doubleValue());
//		request.setPayTypeEnum(bestPayTypeEnum);

//		PayResponse response = bestPayService.pay(request);
//		log.info("发起支付 response={}", response);

		return null;

	}


	@Override
	public String asyncNotify(int orderId) {

		PayInfo payInfo = payInfoMapper.selectByOrderNo((long) orderId);
		if (payInfo == null) {
			//告警
			throw new RuntimeException("通过orderNo查询到的结果是null");
		}
		//如果订单支付状态不是"已支付"
		if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())) {
			//Double类型比较大小，精度。1.00  1.0
			//3. 修改订单支付状态
			payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
			payInfoMapper.updateByPrimaryKeySelective(payInfo);
		}
		//TODO pay发送MQ消息，mall接受MQ消息
		amqpTemplate.convertAndSend(QUEUE_PAY_NOTIFY, new Gson().toJson(payInfo));
		return "success";
	}

	@Override
	public PayInfo queryByOrderId(String orderId) {
		return payInfoMapper.selectByOrderNo(Long.parseLong(orderId));
	}
}
