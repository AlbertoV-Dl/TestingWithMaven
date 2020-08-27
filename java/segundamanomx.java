import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.net.URLEncoder;
import java.util.Base64;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;


public class segundamanomx {

    @Test
    public void get_token_status_fail_test(){
        given().queryParam("lang","es").when().log().all()
                .post("https://webapi.segundamano.mx/nga/api/v1.1/private/accounts")
                .then().statusCode(400);
    }

    @Test
    public void get_token(){

        String email = "apitest@mailinator.com";
        String pass = "12345";
        String ToEncode = email + ":" + pass;

        String Basic_encoded = Base64.getEncoder().encodeToString(ToEncode.getBytes());

        RestAssured.baseURI=String.format("https://webapi.segundamano.mx/api/v1.1/private/account");
        Response response=given().queryParam("lang","es").log().all().
                header("Authorization","Basic "+ Basic_encoded)
                .post();

        String body = response.getBody().asString();
        System.out.println("Value body: " + response.getBody().asString());

        assertEquals(301, response.getStatusCode());
        assertNotNull(body);
        assertTrue(body.contains("access_token"));

    }
}
