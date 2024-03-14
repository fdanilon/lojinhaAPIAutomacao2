package modulos.produto;

import dataFactory.ProdutoDataFactory;
import dataFactory.UsuarioDataFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ComponentePojo;
import pojo.ProdutoPojo;
import pojo.UsuarioPojo;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do modulo de produto")
public class ProdutoTest {

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
    @DisplayName("Validar que o valor do produto igual a 0.0  nao e permitido")
    public void testValidarLimitesZeradoProibidosValorProduto(){
        // Tentar inserir um produto com valor 0.00 e validar que a mensagem de erro
        // foi apresentada e o status code retornado foi 422
        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
            .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(0.00))
            .when()
                .post("/v2/produtos")
            .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);
    }

    @Test
    @DisplayName("Validar que o valor do produto maior que 7000.0  nao e permitido")
    public void testValidarLimitesMaiorSeteMilProibidosValorProduto() {
        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(7000.01))
            .when()
                .post("/v2/produtos")
            .then()
                .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
    }

    @Test
    @DisplayName("Validar que o valor do produto maior que 7000.0  nao e permitido")
    public void testCriarProduto() {
        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
            .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(3.0))
        .when()
            .post("/v2/produtos")
        .then()
            .assertThat()
                .statusCode(201)
                .body("message", equalTo("Produto adicionado com sucesso"));
    }

    @Test
    @DisplayName("Alterar o valor de um produto")
    public void testAlterarValorProduto(){
        //Busca do produtoId
        int produtoId = given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
        .when()
            .get("/v2/produtos")
        .then()
            .extract()
                .path("data.produtoId[0]");
        //alteração de um produto
        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
            .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(0.01))
        .when()
            .put("/v2/produtos/"+produtoId)
        .then()
            .assertThat()
            .statusCode(200)
            .body("message", equalTo("Produto alterado com sucesso"));
        //verificação da alteração
        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
        .when()
            .get("/v2/produtos")
        .then()
            .assertThat()
                .body("data.produtoValor[0]", equalTo(0.01F));
    }
}
