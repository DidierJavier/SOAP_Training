package co.com.sofka.stepdefinition.calculator.multiplynumber;

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

public class CalculatorMultiplyStepDefinition extends Setup {
    private static final Logger LOGGER = Logger.getLogger(CalculatorMultiplyStepDefinition.class);
    private static final String CALCULATOR_MULTIPLY_XML = "src/test/resources/file/calculatorsubtractandmultiply/calculatorMultiply.xml";
    private static final String NUM1_MULTIPLY = "[num1]";
    private static final String NUM2_MULTIPLY = "[num2]";
    private String bodyRequest;

    @Dado("que el usuario esta en el recurso para multiplicar los números {string} y {string}")
    public void queElUsuarioEstaEnElRecursoParaMultiplicarLosNumerosY(String num1, String num2) {
        setUpLog4j2();
        actor.can(CallAnApi.at(CALCULATOR_URL_BASE));
        headers.put("Content-Type", "text/xml;charset=UTF-8");
        headers.put("SOAPAction", "http://tempuri.org/Multiply");
        bodyRequest = defineBodyRequest(num1, num2);
        LOGGER.info(bodyRequest);
    }

    @Cuando("el usuario genera el calculo")
    public void elUsuarioGeneraElCalculo() {
        actor.attemptsTo(
                doPost()
                        .usingTheResource(CALCULATOR_RESOURCE)
                        .withHeaders(headers)
                        .andBodyRequest(bodyRequest)
        );
    }

    @Entonces("visualizará el resultado calculado de ambos números")
    public void visualizaraElResultadoCalculadoDeAmbosNumeros() {
        String soapResponse = new String(LastResponse.received().answeredBy(actor).asByteArray(), StandardCharsets.UTF_8);
        LOGGER.info(soapResponse);
        actor.should(
                seeThatResponse(
                        "El código de respuesta debe ser: " + HttpStatus.SC_OK,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_OK)
                ),
                seeThat(
                        "El mensaje debe ser " + MULTIPLY_RESULT,
                        returnStringValue(soapResponse),
                        containsString(MULTIPLY_RESULT)
                )
        );
    }

    private String defineBodyRequest(String num1, String num2){
        return readFile(CALCULATOR_MULTIPLY_XML).replace(NUM1_MULTIPLY, num1).replace(NUM2_MULTIPLY, num2);
    }
}
