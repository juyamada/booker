import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given; // função given
import static io.restassured.RestAssured.unregisterParser;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;          // classe de verificadores do Hamcrest


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TesteUsuario {

    // Configura 

        static String ct = "application/json";
        static String uri = "https://restful-booker.herokuapp.com/auth";
        
        String jsonBody = """
        {
        "username": "admin",
        "password": "password123"
        }
        """;
        String token = "abc123";

    @Test @Order(1)
    public void criarUsuario(){

        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
    // Executa
        .when()
            .post(uri)
    // Valida
        .then()
            .log().all()
            .statusCode(200)
            .body("token", notNullValue())  // Verifica apenas se o valor da response não é nulo já que o valor do token irá mudar a cada requisição
        ;
    }
    @Test @Order(2)
    public void cadastrarLivro(){
        // Configura
        String uri = "https://restful-booker.herokuapp.com/booking";
        
        // Executa

        // Valida

    }
}