package co.com.sofka.stepdefinition.calculator.subtractnumber;

import co.com.sofka.setup.Setup;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import java.nio.charset.StandardCharsets;
import static co.com.sofka.questions.ReturnStringValue.returnStringValue;
import static co.com.sofka.tasks.DoPost.doPost;
import static co.com.sofka.utils.Dictionary.*;
import static co.com.sofka.utils.FileUtilities.readFile;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.containsString;

public class CalculatorSubtractStepDefinition extends Setup{
    private static final Logger LOGGER = Logger.getLogger(CalculatorSubtractStepDefinition.class);
    private static final String CALCULATOR_SUBTRACT_XML = "src/test/resources/file/calculatorsubtractandmultiply/calculatorSubtract.xml";
    private static final String NUM1_SUBTRACT = "[number1]";
    private static final String NUM2_SUBTRACT = "[number2]";
    private String bodyRequest;

    @Dado("que el usuario esta en el recurso para restar los números {string} y {string}")
    public void queElUsuarioEstaEnElRecursoParaRestarLosNumerosY(String number1, String number2) {
        setUpLog4j2();
        actor.can(CallAnApi.at(CALCULATOR_URL_BASE));
        headers.put("Content-Type", "text/xml;charset=UTF-8");
        headers.put("SOAPAction", "http://tempuri.org/Subtract");
        bodyRequest = defineBodyRequestSubtract(number1, number2);
        LOGGER.info(bodyRequest);
    }

    @Cuando("el usuario genera el calculo de la resta")
    public void elUsuarioGeneraElCalculoDeLaResta() {
        actor.attemptsTo(
                doPost()
                        .usingTheResource(CALCULATOR_RESOURCE)
                        .withHeaders(headers)
                        .andBodyRequest(bodyRequest)
        );
    }

    @Entonces("visualizará el resultado restado de ambos números")
    public void visualizaraElResultadoRestadoDeAmbosNumeros() {
        String soapResponse = new String(LastResponse.received().answeredBy(actor).asByteArray(), StandardCharsets.UTF_8);
        LOGGER.info(soapResponse);
        actor.should(
                seeThatResponse(
                        "El código de respuesta debe ser: " + HttpStatus.SC_OK,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_OK)
                ),
                seeThat(
                        "El mensaje debe ser " + SUBTRACT_RESULT,
                        returnStringValue(soapResponse),
                        containsString(SUBTRACT_RESULT)
                )
        );
    }

    private String defineBodyRequestSubtract(String number1, String number2){
        return readFile(CALCULATOR_SUBTRACT_XML).replace(NUM1_SUBTRACT, number1).replace(NUM2_SUBTRACT, number2);
    }
}
