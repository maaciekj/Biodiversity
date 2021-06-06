package biodiversity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Arrays;

public class Log4UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

    private static final Logger logger = LogManager.getLogger(Log4UncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error(Arrays.deepToString(e.getStackTrace()));
    }
}
