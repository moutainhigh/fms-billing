/**
 * Author : czy
 * Date : 2019年6月19日 上午8:21:54
 * Title : com.riozenc.billing.web.config.MongoCollectionConfig.java
 *
**/
package org.fms.billing.server.web.config;

public enum MongoCollectionConfig {

	USER_INFO, // 用户档案

	WRITE_SECT, // 抄表区段

	WRITE_FILES, // 抄表单表

	ELECTRIC_METER, // 电计量点
	ELECTRIC_METER_REL, // 电计量点套扣关系表
	ELECTRIC_METER_REPLACE, // 电计量点换表记录

	ELECTRIC_METER_MONEY, // 电计量点电费明细表***

	METER_METER_ASSETS_REL, // 计量点与电能表关系表
	METER_INDUCTOR_ASSETS_REL, // 计量点与互感器关系表

	TRANSFORMER_INFO, // 变压器1
	TRANSFORMER_METER_REL, // 变压器与计量点关系表

	TRANSFORMER_LOSS_FORMULA_PARAM_INFO, // 变压器损耗参数表-公式
	TRANSFORMER_LOSS_TABLE_PARAM_INFO, // 变压器损耗参数表-查表

	COS_STANDARD_CONFIG, // 电费力率调整表

	WATER_METER, // 水计量点

	PRICE_EXECUTION, // 电价执行表
	PRICE_LADDER_RELA, // 电价阶梯表

	SYSTEM_COMMON, // 系统参数

	ARREARAGE_INFO,// 欠费表

	PRE_CHARGE,
}
