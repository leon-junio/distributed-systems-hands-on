# Distributed Systems Hands-On

Este repositório contém exemplos práticos de comunicação entre sistemas distribuídos utilizando diferentes tecnologias como RMI, gRPC, Kafka, Redis e Spring Boot. Cada pasta apresenta um enfoque distinto, com o diretório `final_example` consolidando todas essas abordagens em uma solução unificada.

---

## 📂 Estrutura do Projeto

### `RPC/`
Implementação de chamadas remotas usando **Java RMI**. Demonstra como expor e consumir um serviço simples de forma remota.

### `gRPC/`
Exemplo de servidor e cliente usando **gRPC**. Aqui você encontra a definição do `.proto` e sua implementação em Java.

### `kafka/`
Contém um produtor e um consumidor Kafka, com exemplos em **Python**, utilizados para processar cálculos de Fibonacci via mensagens Kafka.

### `docker/`
Arquivos Docker Compose para facilitar a configuração de **Apache Kafka e Zookeeper** em ambiente local.

### `final_example/`
Solução final usando **Spring Boot**, **Redis Cache**, **Kafka** e integrações com gRPC e RMI. É um exemplo completo de como criar um sistema distribuído com cache e troca de mensagens assíncronas.
Simulação completa de um pipeline de envio de mensagens.

---

## 📚 Tecnologias Usadas

- Java 17+
- Python 3+
- Spring Boot
- Apache Kafka
- Redis
- gRPC
- RMI (Java)
- Docker e Docker Compose

---

## 🚀 Como Executar

A execução dos exemplos é **específica para cada pasta**.

### Exemplo para subir as instancias de docker:

```bash
cd docker/
docker-compose up -d
```

### Gerar estrutura proto para o gRPC do java

```bash
mvn compile
```

### Gerar estrutura proto para o gRPC do python

```bash
python -m grpc_tools.protoc -I./proto --python_out=. --pyi_out=. --grpc_python_out=. ./proto/compute.proto
```
