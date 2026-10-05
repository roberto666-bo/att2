package org.example;

import java.util.HashMap;
import java.util.Map;

public class LoginService {

    private final ValidarSenha validarSenha = new ValidarSenha();

    // Simulação de repositório de usuários e estado de tentativas
    private final Map<String, String> usuarios = new HashMap<>();
    private final Map<String, Integer> tentativasFalhas = new HashMap<>();
    private final Map<String, Boolean> usuariosBloqueados = new HashMap<>();

    public LoginService() {
        // Usuários cadastrados para cenários de teste
        usuarios.put("admin", "Java@12345");
        usuarios.put("usuarioBloqueado", "Java@12345");

        tentativasFalhas.put("admin", 0);
        tentativasFalhas.put("usuarioBloqueado", 3);
        usuariosBloqueados.put("admin", false);
        usuariosBloqueados.put("usuarioBloqueado", true);
    }

    public boolean autenticar(String usuario, String senha) {
        // Validações de entradas nulas ou em branco
        if (usuario == null || usuario.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            return false;
        }

        // Verifica se o usuário existe
        if (!usuarios.containsKey(usuario)) {
            return false;
        }

        // Verifica se a conta já está bloqueada
        if (isBloqueado(usuario)) {
            return false;
        }

        // Valida requisitos e correspondência de senha
        boolean senhaFormatoValido = validarSenha.validarSenha(senha);
        boolean senhaCorreta = senhaFormatoValido && usuarios.get(usuario).equals(senha);

        if (senhaCorreta) {
            tentativasFalhas.put(usuario, 0); // Reseta o contador em caso de sucesso
            return true;
        } else {
            // Incrementa falhas e aplica bloqueio na 3ª tentativa incorreta
            int falhas = tentativasFalhas.getOrDefault(usuario, 0) + 1;
            tentativasFalhas.put(usuario, falhas);

            if (falhas >= 3) {
                usuariosBloqueados.put(usuario, true);
            }
            return false;
        }
    }

    public boolean isBloqueado(String usuario) {
        return usuariosBloqueados.getOrDefault(usuario, false);
    }

    public int getTentativasFalhas(String usuario) {
        return tentativasFalhas.getOrDefault(usuario, 0);
    }
}
