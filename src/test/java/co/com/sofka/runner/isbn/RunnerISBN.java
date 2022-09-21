package co.com.sofka.runner.isbn;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {"src/test/resources/feature/isbn/isbn10.feature"},
        glue = {"co.com.sofka.stepdefinition.isbn"},
        tags = ""
)

public class RunnerISBN {
}
