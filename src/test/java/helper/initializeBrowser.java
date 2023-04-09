package helper;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import objectRepository.BasePage;


public class initializeBrowser extends BasePage {

    @Before //Cucumber Before Hook
    public static void setupDriver() throws InterruptedException {
        System.out.println("In B4");
        WebDriverManager.chromedriver().setup();
        driver.manage().window().maximize();
    }

    @After // Cucumber After hook
    public static void quitDriver() throws Exception {
        System.out.println("In After");
       try {
           driver.close();
           driver.quit();
       }catch (Exception e){
           System.out.println("=====>Exception======?>");
       }



    }
}
