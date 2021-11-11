# episen-ms-security - Elisa FOLLET - ING3 FISE

###### Docker 

Le fichier Dockerfile permet de :
- Mettre à jour le repository distant du container
- Installer les paquets requis pour le projet (maven et git)
- Récupérer le code présent dans ce repository
- Générer l'executable avec maven
- Définir la commande de lancement du container

Pour exécuter le fichier :

$ docker build -t episen-ms-security .

De cette façon, l'image est tagée avec "episen-ms-security"
On peut ensuite vérifier si l'image a été construite correctement avec :

$ docker images | grep episen-ms-security

Si c'est le cas, nous pouvons lancer le container comme ceci :

$ docker run -d --name episen-ms-security -p 8070:8070 <id-image>

L'application se lance avec le port 8070 du container. Il faut donc s'assurer que le mapping est correct. Ici, nous aurions pu mettre un autre port pour notre machine.
Nous pouvons vérifier que le container s'est correctement construit :

$ docker ps -a | grep episen-ms-security

Enfin, nous pouvons aussi faire une vérification sur le lancement de l'application comme cela :

$ docker logs -f episen-ms-security

Le container est lancé ainsi que notre application, nous pouvons passer aux tests des endpoints.


###### Test Rest API

Tous les tests de l'API peuvent se réaliser sur Postman. 
Nous allons d'abord tester l'endpoint qui récupère un username ainsi qu'un password et génère le JWT correspondant. Pour cela, il faut faire une requête POST telle que :
  - URL : localhost:8070/authenticate
  - Dans Headers, ajouter : Key: "Content-Type" et Value: "json/application"
  - Body : 
  {
    "username": "toto",
    "password": "toto"
  }
  
Cela nous donne comme réponse :
  "jwt": "[header].[payload].[signature]"
  
Une fois le JWT récupéré, nous pouvons le réutiliser pour tester le second endpoint qui récupère un JWT existant et donne l'accès à une URL, dans Postman, réaliser une requête POST avec :
  - URL : localhost:8070/hello
  - Body :
    {
    "jwt": "[header].[payload].[signature]" 
    }
  - Dans Header, ajouter : Key: "Authorization" et Value: "Bearer [header].[payload].[signature]" 

"[header].[payload].[signature]" correspond au jwt récupéré avec /authenticate
 Cela nous donne comme réponse :
  - "toto"
Il s'agit du seul utilisateur autorisé à se logger dans notre application
 
