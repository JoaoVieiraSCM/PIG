FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copia os arquivos necessários
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src ./src

# Concede permissão de execução ao mvnw
RUN chmod +x mvnw

# Executa o build do Maven, ignorando os testes
RUN ./mvnw package -DskipTests

# Define o comando para iniciar o aplicativo
CMD ["java", "-jar", "target/sitebd-0.0.1-SNAPSHOT.war"]