package co.com.sofka.setup;

import net.serenitybdd.screenplay.Actor;
import org.apache.log4j.PropertyConfigurator;
import java.util.HashMap;
import static co.com.sofka.utils.Actor.ACTOR_NAME;
import static co.com.sofka.utils.Log4jValues.LOG4J_PROPERTIES_FILE_PATH;

public class Setup {

    protected final HashMap<String, Object> headers = new HashMap<>();
    protected final Actor actor = Actor.named(ACTOR_NAME.getValue());

    protected void setUpLog4j2() {
        PropertyConfigurator.configure(LOG4J_PROPERTIES_FILE_PATH.getValue());
    }
}
