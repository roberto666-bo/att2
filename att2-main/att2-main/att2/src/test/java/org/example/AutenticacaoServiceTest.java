package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Atividade 6 - Testes do Módulo de Autenticação")
public class AutenticacaoServiceTest {

    private LoginService loginService;

    @BeforeEach
    void setUp() {
        loginService = new LoginService();
    }

    @Test
    @DisplayName("AUT01 - Usuário válido + senha válida")
    void aut01_UsuarioValidoSenhaValida() {
        boolean resultado = loginService.autenticar("admin", "Java@12345");
        assertTrue(resultado, "Deveria autenticar com sucesso.");
    }

    @Test
    @DisplayName("AUT02 - Usuário inexistente")
    void aut02_UsuarioInexistente() {
        boolean resultado = loginService.autenticar("usuarioInexistente", "Java@12345");
        assertFalse(resultado, "Deveria rejeitar usuário inexistente.");
    }

    @Test
    @DisplayName("AUT03 - Senha inválida")
    void aut03_SenhaInvalida() {
        boolean resultado = loginService.autenticar("admin", "SenhaErrada@1");
        assertFalse(resultado, "Deveria rejeitar senha incorreta.");
    }

    @Test
    @DisplayName("AUT04 - Usuário nulo")
    void aut04_UsuarioNulo() {
        boolean resultado = loginService.autenticar(null, "Java@12345");
        assertFalse(resultado, "Deveria rejeitar usuário nulo.");
    }

    @Test
    @DisplayName("AUT05 - Senha nula")
    void aut05_SenhaNula() {
        boolean resultado = loginService.autenticar("admin", null);
        assertFalse(resultado, "Deveria rejeitar senha nula.");
    }

    @Test
    @DisplayName("AUT06 - Usuário vazio")
    void aut06_UsuarioVazio() {
        boolean resultado = loginService.autenticar("", "Java@12345");
        assertFalse(resultado, "Deveria rejeitar usuário vazio.");
    }

    @Test
    @DisplayName("AUT07 - Senha vazia")
    void aut07_SenhaVazia() {
        boolean resultado = loginService.autenticar("admin", "");
        assertFalse(resultado, "Deveria rejeitar senha vazia.");
    }

    @Test
    @DisplayName("AUT08 - Usuário bloqueado")
    void aut08_UsuarioBloqueado() {
        boolean resultado = loginService.autenticar("usuarioBloqueado", "Java@12345");
        assertFalse(resultado, "Deveria rejeitar autenticação de usuário previamente bloqueado.");
    }

    @Test
    @DisplayName("AUT09 - 1ª tentativa inválida")
    void aut09_PrimeiraTentativaInvalida() {
        loginService.autenticar("admin", "SenhaIncorreta1");
        assertFalse(loginService.isBloqueado("admin"), "Usuário deve permanecer desbloqueado na 1ª falha.");
        assertEquals(1, loginService.getTentativasFalhas("admin"));
    }

    @Test
    @DisplayName("AUT10 - 2ª tentativa inválida")
    void aut10_SegundaTentativaInvalida() {
        loginService.autenticar("admin", "SenhaIncorreta1");
        loginService.autenticar("admin", "SenhaIncorreta2");
        assertFalse(loginService.isBloqueado("admin"), "Usuário deve permanecer desbloqueado na 2ª falha.");
        assertEquals(2, loginService.getTentativasFalhas("admin"));
    }

    @Test
    @DisplayName("AUT11 - 3ª tentativa inválida")
    void aut11_TerceiraTentativaInvalida() {
        loginService.autenticar("admin", "SenhaIncorreta1");
        loginService.autenticar("admin", "SenhaIncorreta2");
        loginService.autenticar("admin", "SenhaIncorreta3");
        assertTrue(loginService.isBloqueado("admin"), "Usuário deve ser bloqueado após a 3ª falha.");
    }

    @Test
    @DisplayName("AUT12 - Tentativa após bloqueio")
    void aut12_TentativaAposBloqueio() {
        loginService.autenticar("admin", "SenhaErrada1");
        loginService.autenticar("admin", "SenhaErrada2");
        loginService.autenticar("admin", "SenhaErrada3");

        boolean resultado = loginService.autenticar("admin", "Java@12345");
        assertFalse(resultado, "Deve rejeitar qualquer tentativa de login mesmo com senha correta se estiver bloqueado.");
    }
}