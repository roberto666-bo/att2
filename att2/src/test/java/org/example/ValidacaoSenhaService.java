package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes Caixa Branca - Validação de Senha")
public class ValidacaoSenhaService {

    private ValidarSenha service;

    @BeforeEach
    void setUp() {
        service = new ValidarSenha();
    }

    @Test
    @DisplayName("CT01 - Deve aceitar senha válida com tamanho intermediário")
    void ct01_DeveAceitarSenhaValida() {
        String senhaValida = "Java@12345"; // 10 caracteres
        boolean resultado = service.validarSenha(senhaValida);
        assertTrue(resultado, "A senha deveria ser aceita por atender a todas as regras.");
    }

    @Test
    @DisplayName("CT02 - Deve rejeitar senha com 9 caracteres (Abaixo do limite mínimo)")
    void ct02_DeveRejeitarSenhaMenorQue10Caracteres() {
        String senhaCurta = "Java@1234"; // 9 caracteres
        boolean resultado = service.validarSenha(senhaCurta);
        assertFalse(resultado, "A senha deveria ser rejeitada por ter menos de 10 caracteres.");
    }

    @Test
    @DisplayName("CT03 - Deve rejeitar senha com 13 caracteres (Acima do limite máximo)")
    void ct03_DeveRejeitarSenhaMaiorQue12Caracteres() {
        String senhaLonga = "Java@12345678"; // 13 caracteres
        boolean resultado = service.validarSenha(senhaLonga);
        assertFalse(resultado, "A senha deveria ser rejeitada por ter mais de 12 caracteres.");
    }

    @Test
    @DisplayName("CT04 - Deve rejeitar senha sem números")
    void ct04_DeveRejeitarSenhaSemNumero() {
        String senhaSemNumero = "Java@Testes"; // 11 caracteres, sem número
        boolean resultado = service.validarSenha(senhaSemNumero);
        assertFalse(resultado, "A senha deveria ser rejeitada por não possuir nenhum número.");
    }

    @Test
    @DisplayName("CT05 - Deve rejeitar senha sem letras")
    void ct05_DeveRejeitarSenhaSemLetra() {
        String senhaSemLetra = "123456@7890"; // 11 caracteres, sem letra
        boolean resultado = service.validarSenha(senhaSemLetra);
        assertFalse(resultado, "A senha deveria ser rejeitada por não possuir nenhuma letra.");
    }

    @Test
    @DisplayName("CT06 - Deve rejeitar senha sem caractere especial")
    void ct06_DeveRejeitarSenhaSemEspecial() {
        String senhaSemEspecial = "Java1234567"; // 11 caracteres, sem caractere especial
        boolean resultado = service.validarSenha(senhaSemEspecial);
        assertFalse(resultado, "A senha deveria ser rejeitada por não possuir caractere especial.");
    }

    @Test
    @DisplayName("CT07 - Deve rejeitar senha nula")
    void ct07_DeveRejeitarSenhaNula() {
        boolean resultado = service.validarSenha(null);
        assertFalse(resultado, "A senha nula deveria ser rejeitada no primeiro 'if'.");
    }

    @Test
    @DisplayName("CT08 - Deve rejeitar senha vazia")
    void ct08_DeveRejeitarSenhaVazia() {
        boolean resultado = service.validarSenha("");
        assertFalse(resultado, "A senha vazia deveria ser rejeitada no primeiro 'if'.");
    }

    @Test
    @DisplayName("CT08B - Deve rejeitar senha composta apenas por espaços em branco")
    void ct08b_DeveRejeitarSenhaComEspacos() {
        boolean resultado = service.validarSenha("          "); // 10 espaços em branco
        assertFalse(resultado, "A senha com espaços em branco deve ser rejeitada pelo isBlank().");
    }

    @Test
    @DisplayName("CT09 - Deve aceitar senha com exatamente 10 caracteres (Fronteira Inferior)")
    void ct09_DeveAceitarSenhaComExatamente10Caracteres() {
        String senha10Chars = "Java@12345"; // 10 caracteres
        boolean resultado = service.validarSenha(senha10Chars);
        assertTrue(resultado, "A senha de 10 caracteres válidos deve ser aceita.");
    }

    @Test
    @DisplayName("CT10 - Deve aceitar senha com exatamente 12 caracteres (Fronteira Superior)")
    void ct10_DeveAceitarSenhaComExatamente12Caracteres() {
        String senha12Chars = "Java@1234567"; // 12 caracteres
        boolean resultado = service.validarSenha(senha12Chars);
        assertTrue(resultado, "A senha de 12 caracteres válidos deve ser aceita.");
    }
}