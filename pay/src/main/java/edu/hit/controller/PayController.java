package edu.hit.controller;

import com.google.zxing.WriterException;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import edu.hit.pojo.PayInfo;
import edu.hit.service.impl.PayServiceImpl;
import edu.hit.util.QRCodeGenerator;
import com.lly835.bestpay.config.WxPayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

	@Autowired
	private PayServiceImpl payService;
	@Value("${Ip}")
	private String ip;
	@Value("${my-path}")
	private String path;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private WxPayConfig wxPayConfig;

	@GetMapping("/create")
	@ResponseBody
	public String create(@RequestParam("orderId") String orderId,
							   @RequestParam("amount") BigDecimal amount
							   ) {
		payService.create(orderId, amount, BestPayTypeEnum.ALIPAY_APP);

		//支付方式不同，渲染就不同, WXPAY_NATIVE使用codeUrl,  ALIPAY_PC使用body
//		Map<String, String> map = new HashMap<>();
//		if (bestPayTypeEnum == BestPayTypeEnum.WXPAY_NATIVE) {
//			map.put("codeUrl", response.getCodeUrl());
//			map.put("orderId", orderId);
//			map.put("returnUrl", wxPayConfig.getReturnUrl());
//			return new ModelAndView("createForWxNative", map);
//		}else if (bestPayTypeEnum == BestPayTypeEnum.ALIPAY_PC) {
//			map.put("body", response.getBody());
//			return new ModelAndView("createForAlipayPc", map);
//		}
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		String address = ip + ":" + request.getLocalPort();
		String payUrl = "http://" + address + "/notify?orderId=" + orderId;
		try {
			QRCodeGenerator
					.generateQRCodeImage(payUrl, 350, 350,
							path + orderId + ".png");
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "http://" + address + "/" + orderId + ".png";
	}

	@PostMapping("/notify")
	@ResponseBody
	public String asyncNotify(@RequestParam int orderId) {
		return payService.asyncNotify(orderId);
	}

	@GetMapping("/queryByOrderId")
	@ResponseBody
	public PayInfo queryByOrderId(@RequestParam String orderId) {
		log.info("查询支付记录...");
		return payService.queryByOrderId(orderId);
	}
}
