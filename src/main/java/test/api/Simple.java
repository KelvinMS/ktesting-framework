package test.api;

import static org.hamcrest.Matchers.*;
import core.ExtentApiTestListeners;
import core.ExtentManager;
import core.assertions.ApiAssertion;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Listeners(ExtentApiTestListeners.class)
public class Simple {
    private ApiAssertion assertion = new ApiAssertion();

    @Test
    private void firsTest(){
        Response resp = get("https://reqres.in/api/users?page=2");
        String body =resp.body().asString();
        String statusLine =resp.statusLine();
        int time = (int) resp.getTime();
        String header =resp.header("Content-Type");
        int statusCode = resp.getStatusCode();

        ExtentManager.info("Header: " +header, "StatusLine: " +statusLine,"Time to response: "+time+"ms");
        assertion.assertEquals(200,statusCode,"Status code:"+statusCode);
    }

    @Parameters({"pokemon"})
    @Test
    private void recievePokemonDetails(@Optional("charmander") String pokemon){
        get("https://pokeapi.co/api/v2/pokemon/"+pokemon+"").then().body("forms.name",hasItem(pokemon));
        Response resp = get("https://pokeapi.co/api/v2/pokemon/"+pokemon+"");
        String body =resp.body().asString();
        String statusLine =resp.statusLine();
        int time = (int) resp.getTime();
        String header =resp.header("Content-Type");
        int statusCode = resp.getStatusCode();

        ExtentManager.info("Header: " +header, "StatusLine: " +statusLine,"Time to response: "+time+"ms");
        assertion.assertEquals(200,statusCode,"Status code:"+statusCode);
    }
}
