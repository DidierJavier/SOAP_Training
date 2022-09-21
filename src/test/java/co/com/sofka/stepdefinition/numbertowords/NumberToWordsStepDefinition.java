package co.com.sofka.stepdefinition.numbertowords;

import co.com.sofka.models.numbertowords.NumberToWordsModel;
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
import static org.hamcrest.Matchers.containsString;

public class NumberToWordsStepDefinition extends Setup {

    private static final Logger LOGGER = Logger.getLogger(NumberToWordsStepDefinition.class);
    private static final String NUMBER_TO_WORDS_XML = "src/test/resources/file/numbertowords/numberToWords.xml";
    private static final String NUMBER = "[number]";
    private static final String URL_BASE = "https://www.dataaccess.com";
    private static final String RESOURCE = "/webservicesserver/NumberConversion.wso";
    private String bodyRequest;

    @Dado("que el usuario se encuentra en el recurso")
    public void queElUsuarioSeEncuentraEnElRecurso() {
        try {
            setUpLog4j2();
            actor.can(CallAnApi.at(URL_BASE));

            headers.put("Content-Type", "text/xml;charset=UTF-8");
            headers.put("SOAPAction", "");

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    // Escenario: Convertir entero positivo a nombre formal
    @Cuando("el usuario indica el entero positivo y genera la consulta")
    public void elUsuarioIndicaElEnteroPositivoYGeneraLaConsulta() {
        try {
            bodyRequest = defineBodyRequest(positiveNumber());
            LOGGER.info(bodyRequest);

            actor.attemptsTo(
                    doPost()
                            .usingTheResource(RESOURCE)
                            .withHeaders(headers)
                            .andBodyRequest(bodyRequest)
            );

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Entonces("visualizará el nombre del número en palabras")
    public void visualizaraElNombreDelNumeroEnPalabras() {
        String soapResponse = new String(LastResponse.received().answeredBy(actor).asByteArray(), StandardCharsets.UTF_8);
        LOGGER.info(soapResponse);

        actor.should(
                seeThatResponse(
                        "El código de respuesta debe ser: " + HttpStatus.SC_OK,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_OK)
                ),
                seeThat(
                        "El nombre del número debe ser: " + NUMBER_CONVERSION_RESULT,
                        returnStringValue(soapResponse),
                        containsString(NUMBER_CONVERSION_RESULT)
                )
        );
    }

    // Convertir entero negativo a palabras
    @Cuando("el usuario indica el número negativo y genera la consulta")
    public void elUsuarioIndicaElNumeroNegativoYGeneraLaConsulta() {
        try {
            bodyRequest = defineBodyRequest(negativeNumber());
            LOGGER.info(bodyRequest);

            actor.attemptsTo(
                    doPost()
                            .usingTheResource(RESOURCE)
                            .withHeaders(headers)
                            .andBodyRequest(bodyRequest)
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Entonces("visualizará un error")
    public void visualizaraUnError() {
        String soapResponse = new String(LastResponse.received().answeredBy(actor).asByteArray(), StandardCharsets.UTF_8);
        LOGGER.info(soapResponse);

        actor.should(
                seeThatResponse(
                        "El código de respuesta debe ser: " + HttpStatus.SC_OK,
                        validatableResponse -> validatableResponse.statusCode(HttpStatus.SC_OK)
                ),
                seeThat(
                        "El error debe ser: " + NUMBER_CONVERSION_ERROR,
                        returnStringValue(soapResponse),
                        containsString(NUMBER_CONVERSION_ERROR)
                )
        );
    }

    private String defineBodyRequest(Integer number) {
        return readFile(NUMBER_TO_WORDS_XML).replace(NUMBER, number.toString());
    }

    private Integer positiveNumber() {
        NumberToWordsModel numberToWordsModel = new NumberToWordsModel();
        return numberToWordsModel.withNumber(POSITIVE_NUMBER);
    }

    private Integer negativeNumber() {
        NumberToWordsModel numberToWordsModel = new NumberToWordsModel();
        return numberToWordsModel.withNumber(NEGATIVE_NUMBER);
    }
}
