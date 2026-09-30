package org.example;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ValidacaoSenhaService {
    private ValidarSenha service =
            new ValidarSenha();

    @Test
    public void deveAceitarSenhaValida() {
        String senha = "Java@12345";
        boolean resultado =
                service.validarSenha(senha);
        assertTrue(resultado);
    }
}