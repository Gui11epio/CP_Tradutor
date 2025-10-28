package br.com.fiap.tradutoria.service;

import br.com.fiap.tradutoria.dto.TranslateResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TranslatorFacadeService {
    private final EthicsFilterService ethicsFilterService;
    private final OpenAiTranslatorService openAiTranslatorService;


    public TranslatorFacadeService(EthicsFilterService ethicsFilterService,
                                   OpenAiTranslatorService openAiTranslatorService) {
        this.ethicsFilterService = ethicsFilterService;
        this.openAiTranslatorService = openAiTranslatorService;
    }


    public Mono<TranslateResponse> translate(String text) {
        if (!ethicsFilterService.isAllowed(text)) {
            return Mono.just(new TranslateResponse(text, "", ethicsFilterService.refusalMessage()));
        }
        return openAiTranslatorService.translatePtToEs(text);
    }
}
