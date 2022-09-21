package co.com.sofka.stepdefinition.isbn;

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
import static co.com.sofka.utils.Dictionary.FALSE;
import static co.com.sofka.utils.Dictionary.TRUE;
import static co.com.sofka.utils.FileUtilities.readFile;
import static co.com.sofka.utils.Utils.generateISBNCode;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class StepDefinitionISBN extends Setup {

    private static final Logger LOGGER = Logger.getLogger(StepDefinitionISBN.class);

    private static final String IS_ISBN_XML =
            "src/test/resources/file/isvalidisbn10/isvalidisbn10.xml";

    private static final String URL_BASE = "http://webservices.daehosting.com";

    private static final String RESOURCE = "/services/isbnservice.wso";

    private static final String IS_ISBN_10_CODE = "[ISBN10]";

    private String bodyRequest;

    private String IS_ISBN_CODE;
    @Given("the librarian entered the application and generated a code")
    public void theLibrarianEnteredTheApplicationAndGeneratedACode() {
        IS_ISBN_CODE = generateISBNCode();
        setUpLog4j2();
        actor.can(CallAnApi.at(URL_BASE));
        headers.put("Content-Type", "application/soap+xml;charset=UTF-8");
        bodyRequest = defineBodyRequest(IS_ISBN_CODE);
        LOGGER.info(bodyRequest);
    }
    @When("the librarian checks if the code is a valid ISBN code")
    public void theLibrarianChecksIfTheCodeIsAValidIsbnCode() {
        actor.attemptsTo(
                doPost()
                        .usingTheResource(RESOURCE)
                        .withHeaders(headers)
                        .andBodyRequest(bodyRequest)
        );
    }
    @Then("the librarian realizes if the code serves as an ISBN code")
    public void theLibrarianRealizesIfTheCodeServesAsAnIsbnCode() {
        String soapResponseISBN = new String(LastResponse.received().answeredBy(actor).asByteArray(), StandardCharsets.UTF_8);
        LOGGER.info(soapResponseISBN);
        actor.should(
                seeThatResponse(
                        "The code of response should be: " + HttpStatus.SC_OK,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_OK)
                ),
                seeThat(
                        returnStringValue(soapResponseISBN),
                        is(notNullValue())
                )

        );
        if(soapResponseISBN.contains(TRUE)){
            LOGGER.info("The code ISBN 10 is correct: " + IS_ISBN_CODE);
        }
        if(soapResponseISBN.contains(FALSE)){
            LOGGER.info("Is not a valid code: " + IS_ISBN_CODE);
        }
    }

    private String defineBodyRequest(String IS_ISBN_CODE){
        return readFile(IS_ISBN_XML).replace(IS_ISBN_10_CODE, IS_ISBN_CODE);
    }
}
