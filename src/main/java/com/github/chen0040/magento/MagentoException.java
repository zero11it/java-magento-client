package com.github.chen0040.magento;

public class MagentoException extends RuntimeException {
    public MagentoException(String message) {
        super(message);
    }

    public MagentoException(String message, Throwable cause) {
        super(message, cause);
    }

    public MagentoException(Throwable cause) {
        super(cause);
    }
}
