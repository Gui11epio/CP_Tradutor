# 🧠 TradutorIA (Português → Espanhol)

## 📘 Visão Geral
O **TradutorIA** é um projeto **Spring Boot**, **Spring AI** e **Thymeleaf**, que utiliza a API da **OpenAI** para traduzir textos do **português para o espanhol**.

O projeto **rejeita assuntos que podem ser considerados ofensivos, como tema racial/ódio**

A projeto possui uma interface simples via Thymeleaf, onde o usuário digita um texto em português e recebe a tradução gerada por IA.



## Membros do Grupo:
- Guilherme Camasmie Laiber de Jesus: 554894
- Fernando Fernandes Prado: 557982
- Pedro Manzo Yokoo: 556115



## 🧩 Tecnologias Utilizadas

| Componente | Descrição |
|-------------|------------|
| **Java 17+** | Linguagem base |
| **Spring Boot 3.5+** | Framework principal |
| **Spring AI** | Integração com modelos da OpenAI |
| **WebFlux (WebClient)** | Comunicação reativa com a API da OpenAI |
| **Thymeleaf** | Template engine para o front-end |
| **Maven/Gradle** | Gerenciamento de dependências |



## 🧾 application.yml

```yaml
logging:
  level:
    org:
      springframework:
        ai:
          chat:
            client:
              advisor: DEBUG

spring:
  main:
    allow-bean-definition-overriding: true

  application:
    name: tradutoria

  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4o-mini
          temperature: 0.2
          max-tokens: 500

    retry:
      max-attempts: 5
      initial-interval: 5
      max-interval: 60

translator:
  openai:
    url: https://api.openai.com/v1/chat/completions
    model: gpt-4o-mini
```

🔹 O campo `translator.openai.url` é usado para o `WebClient` fazer a requisição HTTP diretamente para o endpoint de **Chat Completions da OpenAI** — ou seja, é o endereço da API que o serviço de tradução consome.



## 🚀 Como Executar o Projeto

### 1️⃣ Clone o repositório
```bash
git clone https://github.com/Gui11epio/CP_Tradutor.git
```

### 2️⃣ Configure a variável de ambiente
<img width="997" height="866" alt="image" src="https://github.com/user-attachments/assets/78ade89b-a133-4d04-9dab-e52fd9848fe0" />


### 3️⃣ Execute o projeto


### 4️⃣ Acesse no navegador:
```
http://localhost:8080
```



## 💬 Exemplo de Uso

### 📝 Entrada:
```
Olá! Eu gostaria de saber como está o clima hoje em Madrid.
```

```
Vida longa ao Imperador!
```

```
Estou com fome, vamos sair para comer?
```

## ⚖️ Regras Éticas e Morais
O tradutor segue um conjunto de princípios éticos:
- Não traduz mensagens com discurso de ódio, violência ou conteúdo ilegal.  
- Caso detecte algo impróprio, responde com:
  > "Desculpe, não posso traduzir esse tipo de conteúdo. Por favor, reformule sua frase."



