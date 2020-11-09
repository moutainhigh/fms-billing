package org.fms.billing.common.util;

import java.math.BigDecimal;

@FunctionalInterface
public interface ToBigDecimalFunction <T> {

    BigDecimal applyAsBigDecimal(T value);

}
