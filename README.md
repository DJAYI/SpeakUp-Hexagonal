# SpeakUp

API REST desarrollada con Spring Boot 4, Java 21 y Maven para gestionar cuestionarios por categorías, registrar respuestas y calcular el porcentaje de acierto de cada pregunta.

El proyecto permite crear categorías, agregar preguntas con sus opciones, responder cuestionarios y consultar los resultados. Las preguntas pueden ser de texto libre, opción única u opción múltiple, y cada respuesta se valida contra el tipo de pregunta antes de guardarse.

La aplicación sigue una arquitectura hexagonal: el dominio contiene las reglas de negocio, la capa de aplicación define los casos de uso y puertos, y la infraestructura implementa los adaptadores HTTP y la persistencia en memoria. Las reglas de dependencia se verifican automáticamente con ArchUnit durante las pruebas.

Para ejecutar el proyecto se utiliza el wrapper de Maven incluido: `.\mvnw.cmd spring-boot:run`. Las pruebas se ejecutan con `.\mvnw.cmd test`, que valida tanto la lógica como las reglas de arquitectura.
