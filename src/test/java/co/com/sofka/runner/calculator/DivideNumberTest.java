package co.com.sofka.runner.calculator;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {"src/test/resources/feature/calculator/addNumber.feature"},
        glue = {"co.com.sofka.stepdefinition.calculator.dividenumber"},
        tags = "@Divide"
)

public class DivideNumberTest {
}
