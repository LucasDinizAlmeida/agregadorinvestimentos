<p align="center" width="100%">
    <img width="50%" src="https://github.com/buildrun-tech/buildrun-agregador-de-investimentos/blob/master/images/stock-market.jpg"> 
</p>

<h3 align="center">
 API em JAVA
</h3>

# Gerenciador de Investimentos

Aplicação para gerenciamento de investimentos desenvolvida em **Java Spring** com **MySQL**.

## Funcionalidades
- Criar usuários e cadastrar contas de investimento  
- Associar **stocks** (ações) a cada conta  
- Calcular automaticamente o valor total investido  
- Consumir dados de mercado através da **API Brapi**

---



## 🚀 Como executar o projeto

### Pré-requisitos
- **Java 17** ou superior
- **Maven 3.8+**
- **MySQL** no Docker(A imagem, e as demais configurações estão no arquivo: docker-compose.yml)

### Clonar o repositório
```bash
git@github.com:LucasDinizAlmeida/agregadorinvestimentos.git
cd ./agregadorinvestimentos
```
### API Brapi
- Crie sua conta na plataforma
- Crie seu token e armazene na variável de ambiente TOKEN

### Suba o Banco de dados
```bash
docker compose up -d
```
### Execute o projeto e consuma na porta:8080
```bash
mvn spring-boot:run
```
