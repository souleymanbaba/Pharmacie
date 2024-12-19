# Utiliser une image JRE minimaliste basée sur Eclipse Temurin
FROM eclipse-temurin:21.0.4_7-jre-ubi9-minimal

WORKDIR /app

# Copier le fichier JAR de l'application dans le répertoire de travail
COPY ./target/stage-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Définir l'entrée du conteneur pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
