package org.jap.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Created by nika
*/

public class DataListProviderException extends Throwable{
    private static final Logger log = LogManager.getLogger(DataListProviderException.class);
        public DataListProviderException(String message) {
            super(message);
        }
    }
