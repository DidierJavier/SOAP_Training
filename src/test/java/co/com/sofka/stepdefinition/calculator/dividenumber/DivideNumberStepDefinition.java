package co.com.sofka.stepdefinition.calculator.dividenumber;

import co.com.sofka.setup.Setup;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;

import static co.com.sofka.questions.ReturnStringValue.returnStringValue;
import static co.com.sofka.tasks.DoPost.doPost;
import static co.com.sofka.utils.Dictionary.*;
import static co.com.sofka.utils.FileUtilities.readFile;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.containsString;

public class DivideNumberStepDefinition extends Setup {
    private static final String CALCULATOR_DIVIDE_XML = "src/test/resources/file/calculator/divideOperation.xml";
    private static final Logger LOGGER = Logger.getLogger(DivideNumberStepDefinition.class);
    private static final String NUM1_DIVIDE = "[number1]";
    private static final String NUM2_DIVIDE = "[number2]";
    private String bodyRequest;


    @Given("that the user entered to the divide section on the calculator {string} and {string}")
    public void thatTheUserEnteredToTheDivideSectionOnTheCalculator(String number1, String number2) {
        setUpLog4j2();
        try {
            actor.can(CallAnApi.at(CALCULATOR_URL_BASE));

            headers.put("Content-Type", "text/xml;charset=UTF-8");
            headers.put("SOAPAction", "http://tempuri.org/Divide");
            bodyRequest = defineBodyRequest(number1, number2);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            Assertions.fail(e.getMessage());
        }

    }
    @When("the user enters two number to divide")
    public void theUserEntersTwoNumberToDivide() {
        try {
            actor.attemptsTo(
                    doPost()
                            .usingTheResource(CALCULATOR_RESOURCE)
                            .withHeaders(headers)
                            .andBodyRequest(bodyRequest))
            ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            Assertions.fail(e.getMessage());
        }

    }
    @Then("the system will get the result of the division")
    public void theSystemWillGetTheResultOfTheDivision() {
        String soapResponse = new String(LastResponse.received().answeredBy(actor).asByteArray());
        actor.should(
                seeThatResponse(
                        "The response code must be: " + HttpStatus.SC_OK,
                        validateResponse -> validateResponse.statusCode(HttpStatus.SC_OK)
                ),
                seeThat(
                        returnStringValue(soapResponse), containsString(DIVIDE_RESULT)
                ));
    }

    private String defineBodyRequest(String number1, String number2){
        return readFile(CALCULATOR_DIVIDE_XML).replace(NUM1_DIVIDE, number1).replace(NUM2_DIVIDE, number2);
    }
}
