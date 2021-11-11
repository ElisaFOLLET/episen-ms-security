# episen-ms-security - Elisa FOLLET - ING3 FISE

###### Docker 

Le fichier Dockerfile permet de :
- Mettre à jour le repository distant du container
- Installer les paquets requis pour le projet (maven et git)
- Récupérer le code présent dans ce repository
- Générer l'exécutable avec maven
- Définir la commande de lancement du container

Pour exécuter le fichier :

$ docker build -t episen-ms-security .

De cette façon, l'image est tagée avec "episen-ms-security"
On peut ensuite vérifier si l'image a été construite correctement avec :

$ docker images | grep episen-ms-security

Si c'est le cas, nous pouvons lancer le container comme ceci :

$ docker run -d --name episen-ms-security -p 8070:8070 <id-image>

L'application se lance avec le port 8070 du container. Il faut donc s'assurer que le mapping est correct. Ici, nous aurions pu mettre un autre port pour notre machine.
Nous pouvons vérifer que le container s'est correctement constuit :

$ docker ps -a | grep episen-ms-security

Enfin, nous pouvons aussi faire une vérification sur le lancement de l'application comme cela :

$ docker logs -f bootdocker

Le container est lancée ainsi que notre application, nous pouvons passer aux tests des endpoints.


###### Test Rest API

