package dataFactory;

import pojo.ComponentePojo;

public class ComponenteDataFactory {

    public static ComponentePojo criarComponente(){
        ComponentePojo componente = new ComponentePojo();
        componente.setComponenteNome("controle");
        componente.setComponenteQuantidade(2);

        return componente;
    }
}
