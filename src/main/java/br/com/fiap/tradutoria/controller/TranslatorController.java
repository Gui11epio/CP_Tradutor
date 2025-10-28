package br.com.fiap.tradutoria.controller;

import br.com.fiap.tradutoria.dto.TranslateRequest;
import br.com.fiap.tradutoria.dto.TranslateResponse;
import br.com.fiap.tradutoria.service.TranslatorFacadeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

@Controller
public class TranslatorController {
    private final TranslatorFacadeService facade;


    public TranslatorController(TranslatorFacadeService facade) {
        this.facade = facade;
    }


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("request", new TranslateRequest(""));
        return "index"; // templates/index.html
    }


    @PostMapping("/translate")
    public String translate(@Valid @ModelAttribute("request") TranslateRequest request,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }


        // chamamos o serviço reativo e bloqueamos uma vez para renderizar a página (simplificação)
        Mono<TranslateResponse> mono = facade.translate(request.text());
        TranslateResponse resp = mono.block();


        model.addAttribute("original", resp.original());
        model.addAttribute("translated", resp.translated());
        model.addAttribute("note", resp.note());
        model.addAttribute("request", request);
        return "result"; // templates/result.html
    }
}
