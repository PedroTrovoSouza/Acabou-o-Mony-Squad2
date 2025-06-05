# 💸 Acabou o Mony  
*Microsserviço de Aplicação Financeira para Processamento de Transações em Tempo Real*

---

## 📘 Descrição Técnica

**Acabou o Mony** é uma plataforma fintech desenvolvida com foco em **processamento rápido, seguro e escalável** de transações financeiras. O sistema permite operações de crédito, débito, cadastro de clientes (PF e PJ), criação de cartões e pedidos, além de registrar transações bancárias com latência inferior a 1 segundo.

O projeto visa atender pequenas e grandes empresas, com foco especial em integrações com plataformas de **Live Commerce** e **Conversational Commerce**.

---

## 🎯 Objetivos

- Reduzir o tempo de processamento de transações de 10 horas para menos de 1 segundo.
- Permitir a integração de múltiplos serviços financeiros com segurança e desempenho.
- Suportar picos de demanda com arquitetura escalável.
- Garantir confiabilidade e disponibilidade contínua.

---

## 📐 Arquitetura do Sistema

- **Spring Boot (Java)**: Backend principal, responsável pela API REST, regras de negócio e autenticação.
- **RabbitMQ**: Gerenciamento de filas para comunicação assíncrona e desacoplamento entre microsserviços.
- **Python**: Microserviços especializados em análise de dados e processamento paralelo.
- **MySQL (via JPA + Hibernate)**: Persistência de dados estruturados com alta disponibilidade.
- **Docker**: Containerização para garantir portabilidade e consistência entre ambientes.
- **JWT (JSON Web Token)**: Segurança por meio de autenticação stateless.

---

## 🔐 Segurança

- Comunicação HTTPS com criptografia ponta a ponta.
- Autenticação e autorização via JWT com refresh tokens.
- Roles e permissões para controle granular de acesso.
- DTOs utilizados para proteger dados sensíveis na comunicação.
- Monitoramento de acessos suspeitos e verificação em duas etapas para ações críticas.

---

## 🧪 Testes e Qualidade

- Testes de unidade (JUnit + Mockito)
- Testes de performance com **Locust**
- Testes de integração (SpringBootTest)
- Validações de entrada para evitar vulnerabilidades conhecidas

---

## ⚙️ Funcionalidades

- Cadastro de clientes PF e PJ
- Emissão de cartões digitais
- Cadastro de produtos
- Cadastro de pedidos integrando outras entidades
- Transações financeiras (crédito/débito) em tempo real

---

## 📡 Endpoints REST Principais

| Recurso        | Método HTTP | Descrição                           |
|----------------|-------------|-------------------------------------|
| `/clientes/pf` | `POST`      | Criação de cliente pessoa física    |
| `/clientes/pj` | `POST`      | Criação de cliente pessoa jurídica  |
| `/cartao`      | `POST`      | Criação de cartão digital           |
| `/produtos`    | `POST`      | Cadastro de produtos                |
| `/pedido`      | `POST`      | Criação de pedido completo          |
| `/transacao`   | `POST`      | Registro de transações financeiras  |

---

## 📈 Escalabilidade & Monitoramento

- Mensageria com RabbitMQ garante resiliência e desacoplamento
- Banco de dados configurado com réplicas para alta disponibilidade
- Monitoramento ativo e alertas de performance
- Infraestrutura preparada para autoscaling

---

## 🔄 Integrações

- Integração com plataformas de Live e Conversational Commerce
- API REST com documentação seguindo padrão OpenAPI
- Suporte a webhooks e eventos assíncronos via RabbitMQ

---

## 🧠 Roadmap

- Refatoração contínua do backend com foco em Clean Architecture
- Implementação de cache para reduzir latência em endpoints críticos
- Integração com gateways de pagamento externos
- Consolidação do painel de monitoramento em tempo real

---

## 👨‍💻 Equipe Técnica

- [**Fabio Azevedo**](https://github.com/FabioPojects) 
- [**Fernando Amorim**](https://github.com/FernandoAmoriim) 
- [**João Rossi**](https://github.com/JoaoRossii)
- [**Pedro Trovo**](https://github.com/PedroTrovoSouza)
