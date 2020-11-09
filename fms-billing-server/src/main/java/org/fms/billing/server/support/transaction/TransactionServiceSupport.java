package org.fms.billing.server.support.transaction;

import org.springframework.context.annotation.Configuration;

import com.riozenc.titanTool.spring.transaction.registry.TransactionServiceRegistryPostProcessor;

@Configuration
public class TransactionServiceSupport extends TransactionServiceRegistryPostProcessor {

}