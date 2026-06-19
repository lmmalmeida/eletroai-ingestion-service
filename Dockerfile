# Passo 1: Usar uma imagem base leve do Eclipse Temurin (Java 21)
FROM eclipse-temurin:21-jre-alpine

# Definir o diretório de trabalho dentro do contentor
WORKDIR /app

# Criar um utilizador sem privilégios por questões de segurança no NAS
RUN addgroup -S quarkus && adduser -S quarkus -G quarkus
USER quarkus

# Copiar os ficheiros gerados pelo build do Quarkus (Fast-Jar)
COPY --chown=quarkus:quarkus target/quarkus-app/lib/ /app/lib/
COPY --chown=quarkus:quarkus target/quarkus-app/*.jar /app/
COPY --chown=quarkus:quarkus target/quarkus-app/app/ /app/app/
COPY --chown=quarkus:quarkus target/quarkus-app/quarkus/ /app/quarkus/

# Expor a porta padrão da API
EXPOSE 8081

# Comando para arrancar a aplicação usando o fast-jar
CMD ["java", "-jar", "/app/quarkus-run.jar"]