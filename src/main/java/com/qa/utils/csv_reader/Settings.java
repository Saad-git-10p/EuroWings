package com.qa.utils.csv_reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class Settings {
    private static String getConfigProperties(String key){
        String returnVal = null;

        try{
            Properties properties = new Properties();
            FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties");


            properties.load(file);
            returnVal = properties.getProperty(key).trim();
        }
        catch (IOException x){
            System.out.println(x);
        }
        return  returnVal;
    }

    public static final int WEBDRIVER_DEFAULT_EXPLICIT_TIMEOUT = parseInt(getConfigProperties("web_driver_timeout"));
    public static final String BROWSER_TYPE = getConfigProperties("browser");
    public static final boolean HEADLESS_EXECUTION =Boolean.parseBoolean(getConfigProperties("headlessExecution")) ;
    public static final int IMPLICIT_WAIT_TIMEOUT = parseInt(getConfigProperties("implicit_wait_timeout"));
    public static final int MAX_RETRY_COUNT = parseInt(getConfigProperties("max_retry_count"));
}
