package com.example.voice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jianhui.Yang
 * @version $Id ApiException.java, v 0.1 2018-11-09 14:18 jianhui.Yang Exp $$
 */
public class ApiException extends Exception {


    private static Logger logger = LoggerFactory.getLogger(ApiException.class);


    public ApiException (String message) {
        super(message);
        logger.error(message);
    }

    public ApiException(String message, Throwable cause){
        super(message + cause.getMessage() , cause);
        logger.error(message,cause);
    }

}
