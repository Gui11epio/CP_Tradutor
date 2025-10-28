package br.com.fiap.tradutoria.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EthicsFilterService {
    // Lista de termos proibidos simplificada — adaptar conforme necessidade e legislação
    private final List<String> bannedWords = List.of(
            "terrorismo", "explosivos", "incitamento", "ódio-racial", "ataque" // exemplo
    );


    public boolean isAllowed(String text) {
        if (text == null) return false;
        String lower = text.toLowerCase();
        for (String b : bannedWords) {
            if (lower.contains(b)) return false;
        }
// Checagem simples contra instruções para cometer crimes — regras de negócio
        if (lower.matches(".*\\b(como fabricar|como explodir|como atacar)\\b.*")) return false;
        return true;
    }


    public String refusalMessage() {
        return "Desculpe — não posso ajudar com esse conteúdo por motivos de segurança e ética.";
    }
}
