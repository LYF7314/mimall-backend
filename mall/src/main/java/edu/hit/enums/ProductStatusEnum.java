package edu.hit.enums;

import lombok.Getter;

/**
 * 商品状态.1-在售 2-下架 3-删除
 */
@Getter
public enum ProductStatusEnum {
	// 商品在售
	ON_SALE(1),
	// 商品下架
	OFF_SALE(2),
	// 商品删除
	DELETE(3);

	Integer code;

	ProductStatusEnum(Integer code) {
		this.code = code;
	}
}
