package dataFactory;

import pojo.UsuarioPojo;

public class UsuarioDataFactory {
    public static UsuarioPojo usuarioExistente(){
        UsuarioPojo usuario = new UsuarioPojo();
        usuario.setUsuarioLogin("bianca76");
        usuario.setUsuarioSenha("bianca76");

        return usuario;
    }

    public static UsuarioPojo criaUsuario(){
        UsuarioPojo usuario = new UsuarioPojo();
        usuario.setUsuarioNome("bianca76");
        usuario.setUsuarioLogin("bianca76");
        usuario.setUsuarioSenha("bianca76");

        return usuario;
    }



}
