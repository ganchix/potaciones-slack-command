# potaciones-slack-command[![Build Status](https://travis-ci.org/ganchix/potaciones-slack-command.svg?branch=master)](https://travis-ci.org/ganchix/potaciones-slack-command)[![codecov](https://codecov.io/gh/ganchix/potaciones-slack-command/branch/master/graph/badge.svg)](https://codecov.io/gh/ganchix/potaciones-slack-command)



Extensión de slack que usamos en [Fintonic](https://www.fintonic.com), para votar donde queremos ir a comer los jueves.

Usarla es fácil, solo tienes que desplegar sobre Heroku el software,[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/ganchix/potaciones-slack-command/tree/master&env[MONGO_DATABASE_URL]=UriMongo&env[RESTAURANT_LIST]=Restaurante1,Restaurante2&env[SLACK_ID]=SlackId), usar un servicio de MongoDB tipo mlab.com y configurar el comando en slack.

Configura en tu despliegue de Heroku las siguientes variables:

- MONGO_DATABASE_URL: Uri de la base de datos de mongo.
- RESTAURANT_LIST: Lista de restaurante separados por comas.
- SLACK_ID: Id de slack para el comando

Cuando la configures en slack usa el comando potaciones :D

Una vez tengas los usuarios dados de alta, con que usen el comando una vez ya es suficiente, podrás añadir iconos solo tienes que añadir el atributo icon, por ejemplo:

```json
{
     "_id": "U04G5DZSM",
     "_class": "es.org.ganchix.domain.Person",
     "name": "rafita",
     "icon": ":rafita:"
 }
 ```
Imagenes:

![alt text][logo]

[logo]: https://github.com/ganchix/potaciones-slack-command/raw/master/src/main/resources/potaciones.png "Uso de potaciones"
