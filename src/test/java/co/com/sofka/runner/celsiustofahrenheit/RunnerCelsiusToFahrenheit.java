package co.com.sofka.runner.celsiustofahrenheit;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {"src/test/resources/feature/celsiustofahrenheit/celsiusToFahrenheit.feature"},
        glue = {"co.com.sofka.stepdefinition.celsiusfahrenheit"},
        tags = ""
)

public class RunnerCelsiusToFahrenheit {
}
