package modulos.usuario;

import dataFactory.UsuarioDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

@DisplayName("Testes de API Rest para o modulo de usuario")
public class UsuarioTest {

    private String token;

    @BeforeEach
    public void beforeEach() {
        // configurando os dados da API Rest da Lojinha
        baseURI = "http://165.227.93.41";
        //port = 8080
        basePath = "/lojinha";

        //Obter o token do usuário
        this.token = given()
                        .contentType(ContentType.JSON)
                        .body(UsuarioDataFactory.usuarioExistente())
                    .when()
                       .post("/v2/login")
                    .then()
                        .extract()
                        .path("data.token");
    }

    @Test
    @DisplayName("Validando a criacao de um novo usuario")
    public void testAdicionarNovoUsuario(){
        given()
            .contentType(ContentType.JSON)
            .body(UsuarioDataFactory.criaUsuario())
        .when()
            .post("/v2/usuarios")
        .then()
            .assertThat()
                .statusCode(201);
    }

    @Test
    @DisplayName("Validando a criacao de um mesmo usuario")
    public void testAdicionarUsuarioIgual(){
        String nomeUsuario = UsuarioDataFactory.criaUsuario().getUsuarioNome();
        given()
            .contentType(ContentType.JSON)
            .body(UsuarioDataFactory.criaUsuario())
        .when()
            .post("/v2/usuarios")
        .then()
            .assertThat()
                .statusCode(409)
                .body("error", equalTo("O usuário "+nomeUsuario+" já existe."));
    }
}
