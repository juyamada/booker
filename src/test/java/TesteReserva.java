import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TesteReserva {
    static String ct = "application/json";
    static String uri = "https://restful-booker.herokuapp.com/booking";

    int bookingid; //variável para armazenar o valor do id gerado na reserva

    String firstname = "Juliana";
    String lastname = "Yamada";
    int totalprice = 550;
    boolean depositpaid = true;
    String checkin = "2024-12-01";
    String checkout = "2024-12-20";
    String additionalneeds = "café da manhã";

    public static String lerArquivoJson(String arquivoJson) throws IOException { // ler arquivo json dados de entrada
        return new String (Files.readAllBytes(Paths.get(arquivoJson)));
    }

@Test @Order(1)
public void cadastrarReserva() throws IOException { 
    String jsonBody = lerArquivoJson("src/test/resources/reserva.json");
    
    given()
        .contentType(ct)
        .log().all()
        .body(jsonBody)
    // Executa
    .when()
        .post(uri)
    // Valida
    .then()
        .statusCode(200)
        .log().all()
        .body("bookingid", notNullValue())
        .body("booking.firstname", is (firstname))
        .body("booking.lastname", is (lastname))
        .body("booking.totalprice", is (totalprice))
        .body("booking.depositpaid", is (true))
        .body("booking.bookingdates.checkin", is (checkin))
        .body("booking.bookingdates.checkout", is (checkout))
        .body("booking.additionalneeds", is ("café da manhã"))
        .extract()  // extrai o valor do id da reserva
        .path("bookingid")
        ;
        System.out.println("Booking id gerado: " + bookingid);
        
    
}      

@Test @Order(2)
public void consultarReserva() {
    // Configura
        System.out.println("Consultando id da reserva: " + bookingid );

        given()
            .contentType(ct)
            .log().all()
            
    //Executa
        .when()
            .get(uri + "/" + bookingid)

    //Valida
        .then()
            .log().all()
            .statusCode(200)
            .body("firstname", is(firstname))
            .body("lastname", is (lastname))
            .body("totalprice", is (totalprice))
            .body("depositpaid", is (true))
            .body("bookingdates.checkin", is(checkin))
            .body("bookingdates.checkout", is (checkout))
            .body("additionalneeds", is ("café da manhã"))

            ;




}

}
