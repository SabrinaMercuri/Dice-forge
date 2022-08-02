# Rendu de la deuxième itération
## Fonctionnalités implémentées
Nous avons implementé les fonctionnalités suivantes :
* Les clients se connectent au serveur
* Les clients peuvent communiquer avec le serveur via des messages
* Les clients reçoivent des messages du serveur et du GameManager et les traitent
* Le serveur envoit des messages aux clients en broadcast et unicast
* Le serveur peut recevoir des messages et les traiter
* Les clients sont reliés à un joueur qui leur est propre
* Les clients/joueurs peuvent jouer une partie et des manches
* Ils existent différents types de messages selon les situation 
* Le gagnant est affiché quand la partie prend fin

## Tâches réalisées
L'ensemble des tâches et sous-tâches fixées pour cette deuxième itérations a été réalisé.
* Faire un serveur à connexion multiple
* Faire la connexion du client au serveur
* Faire jouer une partie
* Faire le protocole d'application
* Envoyer des messages du client au serveur
* Dérouler une manche du jeu
* Recevoir les messages du client dans le serveur
* Envoyer des messages du serveur aux clients
* Recevoir les messages du serveur dans le client
* Faire jouer le client

## Difficulté rencontré
La difficulté rencontrée est :
* Race condition sur les threads, c'est à dire que certains threads s'exécutent plus rapidement que d'autres.
* L'ordre de jeu se déroule parfois correctment et parfois le même joueur joue plusieurs fois d'affilé

## Solutions apportés
Les solutions apportées sont : 
* Race condition : un sleep entre chaque nouveau client.
* Aucune solution trouvé pour l'ordre de jeu


## Bilan sur les tests
Nous avons mis en place des classes tests sous JUnit 5 pour les classes qui en nécessitaient. Nous avons testé la logique de la communication Client/Serveur, celle-ci est fonctionnelle et nous avons lancer le réseau (serveur et client) et celui-ci fonctionne aussi.
