package co.com.sofka.runner.numbertowords;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = {"src/test/resources/feature/numbertowords/numberToWords.feature"},
        glue = {"co.com.sofka.stepdefinition.numbertowords"},
        tags = ""
)
public class NumberToWordsRunner {
}
