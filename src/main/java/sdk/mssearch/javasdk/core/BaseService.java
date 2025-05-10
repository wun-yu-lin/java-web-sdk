package sdk.mssearch.javasdk.core;

import org.slf4j.Logger;
import sdk.mssearch.javasdk.logger.SdkLoggerFactory;

public abstract class BaseService {

    private final static Logger logger = SdkLoggerFactory.getLogger(BaseService.class);

    protected final Logger log = SdkLoggerFactory.getLogger(this.getClass());

}
