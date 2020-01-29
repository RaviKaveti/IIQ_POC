package cucumberTests;


import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


//@RunWith(Cucumber.class)
@CucumberOptions(features="src/features",glue= {"stepdefinition"})
public class TestRunner extends AbstractTestNGCucumberTests{

}
