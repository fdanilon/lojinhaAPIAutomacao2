package dataFactory;

import pojo.ComponentePojo;
import pojo.ProdutoPojo;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDataFactory {
    public static ProdutoPojo criarProdutoComumComOValorIgualA(double valor) {
        ProdutoPojo produto = new ProdutoPojo();
        produto.setProdutoNome("Xbox");
        produto.setProdutoValor(valor);
        List<String> cores = new ArrayList<>();
        cores.add("preto");
        cores.add("branco");

        produto.setProdutoCores(cores);
        produto.setProdutoUrlMock("");

////        List<ComponentePojo> componentes = new ArrayList<>();
////        ComponentePojo componente = new ComponentePojo();
////        componente.setComponenteNome("Controle");
////        componente.setComponenteQuantidade(1);
////        componentes.add(componente);
////
////        ComponentePojo componente2 = new ComponentePojo();
////        componente.setComponenteNome("CD");
////        componente.setComponenteQuantidade(3);
////        componentes.add(componente2);
//
//        produto.setComponentes(componentes);

        return produto;
    }
}