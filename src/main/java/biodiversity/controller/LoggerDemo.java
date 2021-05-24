package biodiversity.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoggerDemo {

    private static final Logger logger = LogManager.getLogger(LoggerDemo.class);


    public static void main(String[] args) {

        logger.info("app started");
        logger.error("some error");


    }

}
