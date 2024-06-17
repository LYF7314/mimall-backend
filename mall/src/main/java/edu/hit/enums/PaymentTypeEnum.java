package edu.hit.enums;



import lombok.Getter;

/**
 * 支付类型枚举 1-在线支付

 */
@Getter
public enum PaymentTypeEnum {

	PAY_ONLINE(1),
	;

	Integer code;

	PaymentTypeEnum(Integer code) {
		this.code = code;
	}
}
