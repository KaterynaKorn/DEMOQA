package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ConfigProvider {
    private  static  Properties properties = initProperties();

    private  static  Properties initProperties() {
        Properties prop = new Properties();
        try {
            prop.load(ClassLoader.getSystemResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("File config not found");
        }
        return prop;
    }

    public static WebDriver getDriver(){
        String driverName = Objects.isNull(System.getenv("browser")) ?
        properties.getProperty("browser") : System.getenv("browser");

       return switch (driverName){
            case "chrome" -> new ChromeDriver();

            default -> throw new RuntimeException("Unknown browser");
        };

    }
    public static String getBaseDomain(){ return properties.getProperty("base.url");}
}
