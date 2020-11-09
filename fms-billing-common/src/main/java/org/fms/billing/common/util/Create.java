package org.fms.billing.common.util;

import java.io.IOException;

import org.fms.billing.common.webapp.domain.PriceExecutionDomain;

import com.riozenc.titanTool.common.ClassDAOXmlUtil;

public class Create {
	public static void main(String[] args)  {
		try {
			ClassDAOXmlUtil.buildXML("C:\\Users\\czy\\git\\titan-billing\\src\\main\\java\\com\\riozenc\\billing\\webapp\\dao",
					PriceExecutionDomain.class, "PRICE_EXECUTION_INFO");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("创建成功");
	}
	
}
