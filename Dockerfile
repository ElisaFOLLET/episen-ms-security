FROM openjdk:11

# Mise à jour des repository distant du container, avant d'installer les paquets requis pour le projet
RUN apt update && apt upgrade -y
# Permet d'éviter d'avoir le bug concernant le choix de la timezone
RUN DEBIAN_FRONTEND="noninteractive" apt-get -y install tzdata 

# Installation des paquets requis pour le projet à savoir git et maven
RUN apt-get install -y -q git maven

#Récupération de mon repository Git avec le microservice
RUN git clone https://github.com/ElisaFOLLET/episen-ms-security \
&& cd episen-ms-security/ \
&& mvn clean package

#Lancement de l'application
WORKDIR /episen-ms-security/target/
ENTRYPOINT ["java", "-jar", "episen-ms-security-0.0.1-SNAPSHOT.jar"]

