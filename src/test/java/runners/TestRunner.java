package runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(plugin = {"pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json"},
        tags = "",
        features = {"src/test/resources/features"},
        glue = {"stepDefinition", "helper"}, dryRun = false
        )



public class TestRunner extends AbstractTestNGCucumberTests{
}