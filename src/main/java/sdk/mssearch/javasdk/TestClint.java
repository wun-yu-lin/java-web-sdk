package sdk.mssearch.javasdk;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class TestClint {

    private static final Logger logger = LoggerFactory.getLogger(TestClint.class);

    private String name;

    public static TestClint newInstance(String name) {
        logger.info("new TestClint");
        return new TestClint(name);
    }
}
