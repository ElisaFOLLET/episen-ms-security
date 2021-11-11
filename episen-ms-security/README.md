
# Generate Private Key

Je parle ici de certificat auto-généré et par conséquent non-vérifié par une autorité de certification. C'est une approche tout à fait valable pour des tests mais non recommandée en production.

Un fichier .p12 combine à la fois un certificat et une clé privé. Il faut donc commencer par générer ces 2 éléments:


$ openssl req -x509 -newkey rsa:3072 -keyout rsa_private.pem -nodes -out rsa_cert.pem -subj "/CN=unsused"

Cela génère 2 fichiers:

    rsa_private.pem: la clé privée
    rsa_cert.pem: le certificat

Il ne reste plus qu'à combiner les 2 dans un seul ficher .p12 avec la commande suivante:

$ openssl pkcs12 -inkey rsa_private.pem -in rsa_cert.pem -export -out server.p12 -name episen


Une fois le fichier server.p12 générer on peut le vérifier avec la commande:

$ openssl pkcs12 -in server.p12 -noout -info

###### Docker 


##### Test Rest API

