# worstfilms
API RESTful para possibilitar a leitura da lista de indicados e vencedores da categoria Pior Filme do Golden Raspberry Awards.

Projeto desenvolvido seguindo como referência os requisitos solicitados, onde:

## General info
Projeto desenvolvido seguindo como referência os requisitos solicitados, onde a API deve:
* Ler o arquivo CSV dos filmes e inserir os dados em uma base de dados ao inciar a aplicação.
* Obter o  produtor com  maior  intervalo  entre  dois  prêmios consecutivos.
* Obter o  produtor que ganhou  dois  prêmios mais rápido.

## Technologies
* Java 11
* SpringBoot(JPA, Actuator, rest, web, test, Lombok, JUnit)
* H2 in Memory Database

## Setup on local dev environment
Para rodar o projeto em ambiente local de desenvolvimento basta clonar o projeto e importar em alguma IDE (preferencialmente Intellij).

Executar a clase ``` WorstfilmsApplication ``` para subir a API que ficará disponível em ```http://locahost/8080```

Ao executar esta classe e subir a aplicação o sistema irá invocar o método ```importFilmCSV``` da classe ```FilmService``` importando o arquivo CSV de filmes.
Esse arquivo deve estar no dirétorio ```src/main/resources``` e o seu nome deve ser inserido na variável ```CSV_FILE``` da classe ```FilmService```.

`public static final String CSV_FILE = "movielist.csv";`

#### Running the tests
Para rodar os testes de integração basta rodar a classe ```WorstfilmsApplicationTests``` ou através do comando maven: ```mvn test```


## REST Api
O projeto contém as seguintes chamadas Rests na URL base: `http://localhost:8080/api/texoit/v1/films/`

## Obtêm todos os filmes importados

### Request

URL base

`GET http://localhost:8080/api/texoit/v1/films/`

### Response
```
[
  {
    "year": 1979,
    "title": "Can't covid",
    "studios": "Associated Film Distribution",
    "producers": "Allan Carr",
    "winner": false
  },
  {
    "year": 1979,
    "title": "Can't covid",
    "studios": "Associated Film Distribution",
    "producers": "Allan Carr",
    "winner": false
  },
...
]
```


## Obtêm dados vencedores
Obtêm o  produtor com  maior  intervalo  entre  dois  prêmiosconsecutivos,  e  o  que obteve dois prêmios mais rápido

### Request

URL base

`GET http://localhost:8080/api/texoit/v1/films/find/winners/interval/min/max`

### Response
```
{
  "min": [
    {
      "producer": "Allan Carr",
      "interval": 1,
      "previousWin": 1979,
      "followingWin": 1980
    },
    {
      "producer": "Robert R. Weston",
      "interval": 1,
      "previousWin": 1983,
      "followingWin": 1984
    },
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    }
  ],
  "max": [
    {
      "producer": "Robert R. Weston",
      "interval": 35,
      "previousWin": 1984,
      "followingWin": 2019
    },
    {
      "producer": "Allan Carr",
      "interval": 35,
      "previousWin": 1986,
      "followingWin": 2021
    }
  ]
}
```
### Swagger
Para maiores detalhes da API acessar o swagger pela URL
`http://localhost:8080/swagger-ui/index.html`

## Contact
Created by [@mmazon](https://www.linkedin.com/in/moacirmazon) - feel free to contact me!