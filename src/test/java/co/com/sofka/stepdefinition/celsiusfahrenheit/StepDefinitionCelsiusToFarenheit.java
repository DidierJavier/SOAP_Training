package co.com.sofka.stepdefinition.celsiusfahrenheit;

import co.com.sofka.setup.Setup;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;

import static co.com.sofka.questions.ReturnStringValue.returnStringValue;
import static co.com.sofka.tasks.DoPost.doPost;
import static co.com.sofka.utils.FileUtilities.readFile;
import static co.com.sofka.utils.Utils.convertCelsiusToFahrenheit;
import static co.com.sofka.utils.Utils.generateDegreeCelsius;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.containsString;

public class StepDefinitionCelsiusToFarenheit extends Setup{

    private static final Logger LOGGER = Logger.getLogger(StepDefinitionCelsiusToFarenheit.class);

    private static final String CELSIUS_TO_FARENHEIT_XML =
            "src/test/resources/file/celsiusfahrenheit/celsiusfahrenheit.xml";

    private static final String GRADOS_CELSIUS = "[celsius]";

    private static final String URL_BASE = "https://www.w3schools.com:443";

    private static final String RESOURCE = "/xml/tempconvert.asmx";

    String TEMPERATURE_FAHRENHEIT;

    private String gradosCelsius;
    private String bodyRequest;

    @Given("the scientist measured the temperature to be x degrees Celsius and entered the web resource")
    public void theScientistMeasuredTheTemperatureToBeXDegreesCelsiusAndEnteredTheWebResource() {
        gradosCelsius = generateDegreeCelsius();
        TEMPERATURE_FAHRENHEIT = convertCelsiusToFahrenheit(gradosCelsius);
        setUpLog4j2();
        actor.can(CallAnApi.at(URL_BASE));
        headers.put("Content-Type", "text/xml;charset=UTF-8");
        headers.put("SOAPAction", "https://www.w3schools.com/xml/CelsiusToFahrenheit");
        bodyRequest = defineBodyRequest(gradosCelsius);
        LOGGER.info(bodyRequest);
    }
    @When("the scientist consults the temperature value in degrees Fahrenheit")
    public void theScientistConsultsTheTemperatureValueInDegreesFahrenheit() {
        actor.attemptsTo(
                doPost()
                        .usingTheResource(RESOURCE)
                        .withHeaders(headers)
                        .andBodyRequest(bodyRequest)
        );
    }
    @Then("the scientist gets the temperature conversion in degrees {string} Fahrenheit")
    public void theScientistGetsTheTemperatureConversionInDegreesFahrenheit(String gradosFahrenheit) {
        String soapResponseFahrenheit = new String(LastResponse.received().answeredBy(actor).asByteArray(), StandardCharsets.UTF_8);
        LOGGER.info( soapResponseFahrenheit);
        LOGGER.info(TEMPERATURE_FAHRENHEIT);
        actor.should(
                seeThatResponse(
                        "The code of response should be: " + HttpStatus.SC_OK,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_OK)
                ),
                seeThat(
                        "The temperature in " + gradosFahrenheit + " calculated is iqual to temperature in " +
                                gradosFahrenheit + " obtained",
                        returnStringValue(soapResponseFahrenheit),
                        containsString(TEMPERATURE_FAHRENHEIT)
                )
        );
        LOGGER.info("La temperatura de " + gradosCelsius + " grados celsius, "
                + "corresponde a una temperatura de " + TEMPERATURE_FAHRENHEIT + " grados Fahrenheit");
    }

    private String defineBodyRequest(String gradosCelsius){
        return readFile(CELSIUS_TO_FARENHEIT_XML).replace(GRADOS_CELSIUS, gradosCelsius);
    }
}

