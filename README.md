# route-project
API para controle de rotas

INSTRUÇÕES PARA CONFIGURAR PROJETO

1. O banco de dados utilizado foi o MySQL. Precisa te-lo instalado.
2. Após isto, crie uma senha e um database com o nome ao seu desejo
3. Para alterar isso no código e o sistema se conectar ao banco, é preciso editar o arquivo application.properties, mudando com sua senha e o nome do database escolhido
4. Feito isso, o hibernate irá criar automático as tabelas, inserindo um usuário padrão admin para acessar o sistema (username = "leviporto", senha = 123456)

INSTRUÇÕES PARA UTILIZAÇÃO DO PROJETO

1. Toda requisição feita sem autenticação é barrada pelo spring security.
2. Para autenticar o usuario, é preciso enviar uma requisição POST para http://localhost:8080/auth contendo no body o username e password.
3. Feito isso, o response da requisição conterá no corpo o token para validar qualquer requisição futura.
4. Para testar as requisições como cliente, recomendo utilizar o POSTMAN que faz esse serviço.
5. Após receber o token, acrescentar no Header a cada requisição a key Authorization contendo o Value: Bearer + espaço + {tokenRecebido}.
6. Todas as requisições estarão liberadas após set feito isto.
