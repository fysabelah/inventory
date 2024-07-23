# API para Gerenciamento de Produtos e Reservas

Este repositório foi criado para fins de estudo, no entanto, acabou sendo integrado como parte do _Tech Chalenge 5_ da
minha pós-gradução.

O mesmo faz parte do conjunto de serviço que integram o [_ecommerce_](https://github.com/DFaccio/ecommerce-system).

## Funcionalidades

* Gerencimanto de produtos do tipo: livro, sapatos, roupas e eletrônicos
* Gerenciamento de reservas

## Funcionamento

* Cadastre um produto. Este pode ser deletado ou alterado conforme os metódos.
* Crie reservas para o produto
    * Uma reserva não diminui a quantidade do produto.
    * Uma reserva está limitada a quantidade do produto menos a quantidade reservada menos a proteção
    * Uma reserva pode ter sua quantidade alterado ou cancelada
        * A quantidade será alterada apenas se estiver como READY.
        * Quando a quantidade não está disponível, a quantidade anterior continua reservada e para alteração é retornado
          STOCKOUT.
    * A baixo na quantide de produto só ocorre após a reserva ser confirmada.

## Como executar

Caso pretenda utilizar o projeto completo, favor favor uso do README.md do [
_ecommerce_](https://github.com/DFaccio/ecommerce-system). Agora, para executar apenas
este, siga os passos abaixo.

1. Crie um arquivo .env no diretório root. Este deve ter as chaves abaixo.

```
PROFILE=dev

# MongoDB
MONGO_INITDB_ROOT_USERNAME=ecommerce_admin
MONGO_INITDB_ROOT_PASSWORD=adm!n

# MongoDB Express
ME_CONFIG_BASICAUTH_USERNAME=admin
ME_CONFIG_BASICAUTH_PASSWORD=!nterf@ce#

# Servidor que o Swagger encaminhará as requisições
SWAGGER_SERVER_ADDRESS=http://localhost:7072
```

Obs.: Os valores foram setados por fins de praticidade, no entanto, com exceção do PROFILE, todos valores podem ser
alterado como bem entender.

2. De acordo com o perfil, dev e prod, colocado em PROFILE, sugiro comentar a propriedade
   _eureka.client.serviceUrl.defaultZone_ para
   não ficar apresentando erro de conexão com Eureka Server. O comentário pode ser feito adicionando _#_ na frente da
   propriedade.
4. Digite o comando abaixo.

        docker compose up.

### Informações úteis

* O PROFILE, para fins de desenvolvimento sugiro uso do perfil dev. Caso já tenha o mongo configurado, edite as
  informações
  de conexão como preferir
* Quando este serviço estiver rodando com o projeto completo ele precisará de autenticação.

## Documentação

A documentação pode ser acessada em:

* Formato json: http://localhost:7072/documentation
* Swagger-Ui: http://localhost:7072/doc/products.html

Obs.: Esta informação considera apenas uso deste serviço. Para acesso a documentação considerando a execução de todos
serviços, favor consultar o README.md do [_ecommerce_](https://github.com/DFaccio/ecommerce-system).  