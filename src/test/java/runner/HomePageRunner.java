package runner;

import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


@CucumberOptions(tags = {"@REQ-UI-1_11", "@REQ-UI-12"}, glue = {"stepdefs"}, plugin = {
        "html:target/cucumber-reports/HomePage/cucumber-pretty",
        "json:target/json-cucumber-reports/homepage/cukejson.json",
        "testng:target/testng-cucumber-reports/HomePage/cuketestng.xml"}
        , features = {"src/test/resources/features/"})
public class HomePageRunner extends AbstractTestNGCucumberParallelTests {

    @BeforeClass
    public static void before() {
        System.out.println("Before - " + System.currentTimeMillis());
    }

    @AfterClass
    public static void after() {
        System.out.println("After - " + System.currentTimeMillis());
    }

}


