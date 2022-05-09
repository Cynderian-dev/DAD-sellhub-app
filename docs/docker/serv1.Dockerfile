# Selecciona la imagen base
FROM openjdk:17-jdk-slim
# Define el directorio de trabajo para el comando
WORKDIR /usr/src/app/
# Copia de la aplicación compilada
COPY docs/jars/correointerno-0.0.2.jar /usr/src/app/
# Comando que se ejecuta al hacer docker run
CMD [ "java", "-jar", "correointerno-0.0.2.jar" ]
