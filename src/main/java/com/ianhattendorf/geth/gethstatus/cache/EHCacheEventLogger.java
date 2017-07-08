package com.ianhattendorf.geth.gethstatus.cache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EHCacheEventLogger implements CacheEventListener<Object, Object> {

    private static final Logger logger = LoggerFactory.getLogger(EHCacheEventLogger.class);

    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        logger.debug("Event: {}, Key: {}, Old Value: {}, New Value: {}",
                event.getType(), event.getKey(), event.getOldValue(), event.getNewValue());
    }

}
