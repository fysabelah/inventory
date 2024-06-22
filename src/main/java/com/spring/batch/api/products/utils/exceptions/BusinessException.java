package com.spring.batch.api.products.utils.exceptions;

import com.spring.batch.api.products.utils.MessageUtil;

public class BusinessException extends Exception {

    public BusinessException(String code) {
        super(MessageUtil.getMessage(code));
    }

    public BusinessException(String code, String sku) {
        super(MessageUtil.getMessage(code, sku));
    }
}
