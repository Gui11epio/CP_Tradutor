package br.com.fiap.tradutoria.service;

import br.com.fiap.tradutoria.dto.TranslateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class OpenAiTranslatorService {
    private final WebClient webClient;
    private final String apiKey;
    private final String model;


    public OpenAiTranslatorService(@Value("${translator.openai.url}") String url,
                                   @Value("${OPENAI_API_KEY:}") String apiKey,
                                   @Value("${translator.openai.model:gpt-4o-mini}") String model) {
        this.apiKey = apiKey;
        this.model = model;
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }


    public Mono<TranslateResponse> translatePtToEs(String text) {
// Prompt cuidadosamente construído: instrui o sistema a traduzir e respeitar ética
        String system = "Você é um tradutor profissional de Português para Espanhol. Traduza apenas o texto fornecido mantendo o sentido, tom e estrutura quando possível. Se o texto contiver conteúdo perigoso ou ilegal, recuse educadamente explicando o motivo.";


        String userPrompt = "Traduza para espanhol (pt->es). Seja fiel, claro e respeite ética/moral:\n\nTexto: \"" + text + "\"";


        Map<String, Object> body = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "system", "content", system),
                        Map.of("role", "user", "content", userPrompt)
                ),
                "temperature", 0.2
        );


        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
// resposta simplificada — extrair o conteúdo corretamente conforme a API
                    try {
                        var choices = (List<Map<String, Object>>) response.get("choices");
                        if (choices != null && !choices.isEmpty()) {
                            Map<String, Object> first = choices.get(0);
                            Map<String, Object> message = (Map<String, Object>) first.get("message");
                            String translated = (String) message.get("content");
                            return new TranslateResponse(text, translated.trim(), "ok");
                        }
                    } catch (Exception ex) {
                        return new TranslateResponse(text, "", "Erro na resposta do provedor");
                    }
                    return new TranslateResponse(text, "", "Resposta vazia do provedor");
                })
                ;
    }
}
