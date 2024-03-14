package modulos.componente;

import dataFactory.ComponenteDataFactory;
import dataFactory.UsuarioDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ComponenteTest {
    private String token;
    @BeforeEach
    public void beforeEach() {
        // configurando os dados da API Rest da Lojinha
        baseURI = "http://165.227.93.41";
        //port = 8080
        basePath = "/lojinha";


        //Obter o token do usuário admin
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
    @DisplayName("Adicionar um novo componente ao produto")
    public void testAdicionarComponenteAoProduto(){
        //Busca do produtoId
        int produtoId = given()
                .contentType(ContentType.JSON)
                    .header("token", this.token)
                .when()
                    .get("/v2/produtos")
                .then()
                    .extract()
                    .path("data.produtoId[0]");
        //adicionando um componente ao produto
        given()
            .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ComponenteDataFactory.criarComponente())
        .when()
            .post("/v2/produtos/"+produtoId+"/componentes")
        .then()
            .assertThat()
                .statusCode(201)
                .body("message", equalTo("Componente de produto adicionado com sucesso"));
    }
}
