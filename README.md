# episen-ms-security - Elisa FOLLET - ING3 FISE

Ce projet utilise le framework Spring Security et implémente un JWT basé sur une authentification et une autorisation.

###### Generate Private Key

Certificat auto-généré, non vérifié par une autorité de certification
Approche valable pour des tests mais non recommandée en production

Pour générer un certificat et une clé privée :

$ openssl req -x509 -newkey rsa:3072 -keyout rsa_private.pem -nodes -out rsa_cert.pem -subj "/CN=unsused"

On a à présent :

    rsa_private.pem: la clé privée
    rsa_cert.pem: le certificat

Pour générer le fichier .p12 :

$ openssl pkcs12 -inkey rsa_private.pem -in rsa_cert.pem -export -out server.p12 -name episen

Pour vérifier le fichier server.p12 :

$ openssl pkcs12 -in server.p12 -noout -info

###### Docker 

Le fichier Dockerfile permet de :
- Mettre à jour le repository distant du container
- Installer les paquets requis pour le projet (maven et git)
- Récupérer le code présent dans ce repository
- Générer l'executable avec maven
- Définir la commande de lancement du container

Pour exécuter le fichier (celui-ci doit se trouver dans le répertoire où se trouve le fichier server.p12) :

$ docker build -t episen-ms-security .

De cette façon, l'image est tagée avec "episen-ms-security"
On peut ensuite vérifier si l'image a été construite correctement avec :

$ docker images | grep episen-ms-security

Si c'est le cas, nous pouvons lancer le container comme ceci :

$ docker run -d --name episen-ms-security -p 8070:8070 id-image

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
    "password": "toto",
    "roles": ["manager"]
  }
  
Cela nous donne comme réponse :
  "jwt": "[header].[payload].[signature]"
  
Une fois le JWT récupéré, nous pouvons le réutiliser pour tester le second endpoint qui récupère un JWT existant et donne accès à une URL.
Dans Postman, réaliser une requête POST avec :
  - URL : localhost:8070/hello
  - Dans Header, ajouter : Key: "Authorization" et Value: "Bearer [header].[payload].[signature]" 
  - Body :
    {
    "jwt": "[header].[payload].[signature]" 
    }

"[header].[payload].[signature]" correspond au jwt récupéré avec /authenticate
 Cela nous donne comme réponse :
  - "username : toto"
  
Il s'agit du seul utilisateur autorisé à se logger dans notre application. 
Idéalement, nous pourrions nous connecter à une base de données et récupérer tous les utilisateurs enregistrés.
 
