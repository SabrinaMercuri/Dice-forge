# Découpage initiales du projet

## Itération 1
Un joueur qui utilise un plateau et qui lance un dé à 6 faces. La partie se commence quand le joueur lance le dé et la partie se termine quand il récupère les ressources jusqu'à obtenir 4 points de victoire. La limite de points de victoire est 4. La face du dé est 2 points de victoire.

### Users Story 
#### Debut de partie
En tant que joueur je souhaite débuter la partie au lancement du programme afin que je puisse jouer

Critere d'acceptance

* Créer un plateau
* Créer un joueur

#### Lancer un dé
Description
En tant que joueur,
je souhaite pouvoir lancer les deux dés de mon inventaire
afin de pouvoir obtenir des ressources.

Tests d'acceptances
Scénario :
Etant donné que j'ai lancé mes dés
Et que j'ai obtenu une ressource sur chaque dé
Quand je veux récupérer les ressources
Alors je les récupère

Scénario :
Etant donné que j'ai lancé mes dés
Et que j'ai obtenu une ressource sur un dé et un multiplicateur sur l'autre
Quand je veux récupérer la ressource
Alors je la récupère autant de fois que le multiplicateur

Scénario :
Etant donné que j'ai lancé mes dés
Et que j'ai obtenu un multiplicateur sur les 2 dés
Alors rien ne passe

Scénario :
Etant donné que j'ai lancé mes dés
Et que j'ai obtenu une ressource ou un multiplicateur sur un dé et que j'ai obtenu un portail sur l'autre
Quand je veux récupérer les ressources
Alors je sélectionne une ressource chez un autre joueurs et je la récupère en applicant le multiplicateur si j'en ai obtenu un, sinon je la récupère simplement.

#### Fin de partie
En tant que joueur, je souhaite qu'après avoir récupérer les ressources du lancement de mon dès la partie se mette fin afin de finir le jeu

Critere d'acceptance

Créer un dès
Lancer le dès
Récupérer les ressources dans l'inventaire

### Tâche
#### Création d'un main Plateau
Fais commencer la partie, jouer le joueur et terminer la partie

### Sous-Tâche
#### Création de l'inventaire
Création de la classe Inventaire qui contient des ressources et un dé à une face et des points de victoire .

#### Création des faces
Création de la classe Face qui contient un nombre de ressource et une ressource/multiplicateur/portail

#### Création du Plateau
Création d'une classe Plateau qui sera un main qui lancera la partie et qui lancera les dés du joueurs.

#### Création du joueur
Création de la classe joueur avec un nom et un inventaire.

#### Création du Dé
Création de la classe Dé qui contient un des faces et un nombre de faces.


## Itération 2
Implémentation de la partie réseau client-serveur dans le jeu pour avoir la possibilité d'avoir 4 joueurs connectés au serveur et qui jouent.

### Users Story 
#### Connexion côté serveur
En tant que serveur,
je souhaite pouvoir recevoir des demandes de connexion de la part des clients
afin de pouvoir lancer une partie du jeu diceforge.

Critères d'acceptance

* Une partie ne peut pas dépasser 4 joueurs
* Une partie ne peut pas être lancée avec moins de 2 joueurs
* Pouvoir refuser des connexions
* Pouvoir accepter des connexions
* Recevoir des messages de la part des clients
* Envoyer des messages aux clients


#### Connexion du client au serveur
En tant que client,
je souhaite pouvoir me connecter à un serveur du jeu,
afin de pouvoir jouer une à plusieurs parties de diceforge.

Critère d'acceptance :

* Pouvoir me connecter au serveur
* Detecter si la connexion a été fermé par le serveur
* Savoir si j'ai été accepté par le serveur
* Envoyer des messages au serveur
* Recevoir des messages du serveur


#### Faire une partie avec 4 joueurs
En tant que que joueur,
je souhaite pouvoir jouer avec 3 autres personnes sur une partie à 4 joueurs,
afin de pouvoir jouer à plusieurs.

Note

Le jeu sera une version simplifiée du jeu, à chaque manche tous les joueurs lanceront leurs dès et le premier arrivé à 4 points de victoire à gagner
Si plusieurs joueurs à 4 points de victoire en même temps on en choisira un qui sera déclaré gagnant.

Critère d’acceptance
* Démarrer une partie avec 4 joueurs
* Envoyer la notification de nouvelles manches du serveur au client
* Faire jouer au serveur les dès des joueurs
* Envoyer aux joueurs et les enregistrer
* Recommencez jusqu'à ce que un des joueurs arrivent à 4 points de victoire

### Tâches
#### Faire un serveur à connexion multiple
Créer un serveur capable d'accepter 4 connexions différentes

#### Faire la connexion du client au serveur
Faire se connecter le client au serveur

#### Faire jouer une partie
Lancez des nouvelles manches jusqu'à ce que un des joueurs arrivent à 4 points de victoire

#### Faire le protocole d'application
Définir le protocole d'application pour permettre au client et au serveur de communiquer sur la même base

### Sous-Tâches
#### Envoyer des messages du client au serveur
Envoyer des messages au serveur depuis le client
Cela servira de base pour envoyer les messages du protocole d'application

#### Dérouler une manche du jeu
Faire lancer les dès et enregistrer les scores et les envoyer aux joueurs

#### Recevoir les messages du client dans le serveur
* Recevoir les messages de connexion du client
* Recevoir le nom du client
* Recevoir les actions faite par le client lors d'un tour

#### Envoyer des messages du serveur aux clients
* Envoyer des messages au client depuis le serveur en ciblant un client précis
* Envoyer des messages aux clients en mode broadcast

#### Recevoir les messages du serveur dans le client
* Recevoir les demandes d'informations du serveur concernant les actions du tour
* Recevoir le statut de la partie

#### Faire jouer le client
Les résultats seront enregistrés dans le serveur et envoyé aux client

## Itération 3
Création de la forge. Le joueur aura la possibilité de forger une face d'un de ses dés en utilisant les ressources à sa disposition. (Vérifier si le joueur a le nombre de ressources nécessaires, que la face est disponible, gestion de l'inventaire) Le joueur pourra également utiliser un deuxième dé.

### User Story

#### Forger son dé

En tant que joueur,
je souhaite pouvoir forger un de mes dés en utilisant mes ressources afin de changer la face d'un dé.

Critère d'acceptance :

* Vérifier que le joueur a les ressources nécessaire pour forger
* La face à forger est disponible

#### Modéliser le plateau

En tant que joueur,
je souhaite pouvoir avoir une modélisation du plateau du serveur
afin d'avoir toutes les informations du plateau pour prendre mes décisions.

Critère d'acceptance :

* Modéliser tout les joueurs
* Modéliser la forge et les faces disponibles
* Modéliser les îles
* Modéliser les cartes disponibles

#### Acheter des cartes

En tant que joueur,
je souhaite pouvoir acheter des cartes
afin que je puisse chasser un joueur et améliorer ma stratégie de jeu

Critère d'acceptance :

* Vérifier que les ressources du joueur sont suffisantes pour l'achat
* Vérifier que la carte est disponible
* Vérifier la présence d'un joueur sur l’île, si oui le chasser et lui offrir un lancer de dieu

### Tâche

#### Faire jouer une action au bot

tache de #27 #25

Faire choisir au bot une action aléatoirement,

et lui faire acheter quelque choses dans ses moyens aléatoirement aussi

#### Créer les îles

Tache de l'user story #27

Créer les iles qui contiennent des cartes et la présence ou non d'un joueur

#### Créer le message pour communiquer la carte achetée

Tache de #27

Créer le message pour le protocole pour envoyer au serveur la carte achetée

#### Créer la forge

Tache de l'user story #25

Créer les différentes catégories de faces avec leurs couts associées

#### Créer le message pour communiquer la face achetée

Tache de #25

Créer le message pour le protocole pour que le client puisse indiquer la face qu'il souhaite acheter

#### Modéliser le plateau

Tâche de l'user story #26

Créer un attribut Plateau dans la classe Client.

Récupérer les informations envoyés par le serveur pour mettre à jour l'attribut Plateau dans le joueur

### Sous tâche

#### Mise à jour de la position des joueurs sur les îles

Sous tache de #33

Suite aux messages d'achat d'une carte par un joueur positionner son pion sur l'ile correspondant à la carte

#### Mise à jour des cartes disponibles

Sous tache de #33

Suite aux messages reçues par le serveur quand un joueur achète une carte mettre à jour le plateau en retirant un exemplaire de la carte sur l'ile correspondante et ajouter la carte à l'inventaire du joueur qu'il l'a acheté

#### Positionner le joueur sur une île

Sous tache de #28

Quand un joueur achète une carte sur une île, il place son pion sur cette ile s'il est chassé lui donner un lancer de dés des dieux

#### Mise à jour des faces disponibles dans la forge

Sous tâche de #35

Mettre à jour suite aux messages envoyées par le serveur les faces qui sont encore disponible dans la forge

#### Mise à jour des faces disponibles

Sous tache de #42

Chaque face ne peut être acheter qu'une seule fois par un joueur
Une catégorie contient plusieurs faces qui ne peuvent être toutes achetés qu'une seule fois

Mettre à jour les faces dans les clients & sur le serveur pour savoir si elles peuvent être acheter etc...

#### Créer les cartes

Sous tache de #28

Créer les cartes qui sont un expand de BuyableItem
Ne pas créer les effets, ils seront à faire dans une prochaine itération

#### Chasser le joueur

Sous tâche de #31

Quand le joueur se fait chasser d'une île il lance un lancer de dés des dieux

#### Set des joueurs

Sous tache de #33

Set les joueurs et leurs noms dans le plateau grâce aux informations envoyées par le serveur

#### Mise à jour des ressources 

Sous tâche de #34

Quand le client reçoit un RollMessage ajouter les ressources dans le bon joueur du plateau

#### Créer les catégories de la forge

Sous tache de #39

Créer les catégories de la forge dans lesquels seront mis les faces.

Toutes les faces d'une même catégorie ont le meme prix

#### Créer la classe BuyableItem

Sous tache de #29

Créer la classe abstrait BuyableItem, Face et Card sont un expand de BuyableItem

Liste des ressources à avoir
Liste du nombre de ressource

## Itération 4
Ajouter le plateau au serveur et le faire se mettre à jour.
Lancer plusieurs parties d'affilés, faire des statistiques sur les parties (Fin de la partie au bout de 9 manches).
Plusieurs pom.xml pour le client, le serveur, un programme principal qui lance le serveur et 4 clients.
Dépenser 2 solary stone pour effectuer une nouvelle action à la fin de la première action.

### User Story

#### Nouvelle action

En tant que joueur, je souhaite dépenser 2 solary stone à la fin de ma première action afin de pouvoir refaire une action.

#### Synchronisation de la partie

En tant que serveur, je souhaite que ma partie se déroule dans l'ordre logique afin de pouvoir attendre les réponses des joueurs quant à leurs actions et que les joueurs puissent recevoir les ressources qu'ils ont obtenus


#### Executer le projet 

En tant qu'Utilisateur,
Je souhaite avoir plusieurs éxécutables séparer du projet afin de pouvoir lancer un serveur sur une machine différente des clients, pouvoir lancer le client sur une machine distant ou encore éxécuter le projet dans sa globalité sur ma machine

Critère d'acceptance :

    Éxécutable pour le serveur (pouvoir spécifier le port à ouvrir, si non spécifié port par défaut)
    Éxécutable pour le client ( pouvoir spécifier un host et le port, si non spécifié, connexion en local sur le port par défaut )
    Éxécutable pour lancer le projet ( Lance le serveur et 4 clients qui viendront se connecter dessus )

#### Ajouter un plateau sur le serveur

En tant que serveur,
je souhaite avoir un plateau dans mon GameManager afin de pouvoir vérifier que mes joueurs ne trichent pas et si leurs actions sont valides.

Critère d'acceptance :

    Plateau qui se met à jour au fur et à mesure de l'avancement du jeu

### Tache

#### Synchroniser les étapes de la partie

Tâche de #52

Lorsque que l'input d'un joueur est nécessaire faire que la partie se bloque, c'est l'input du joueur qui débloquera la partie

#### Envoyer le message de demande de nouvelles action

Tâche de l'user story #54

À la fin de la PREMIÈRE action si un joueur possède 2 pierres solaires, lui envoyer un message pour lui demander s'il souhaite les dépenser pour rejouer une action.

#### pom.xml serveur

Créer un pom.xml pour permettre de créer un serveur qui accepte la connexion de clients.

Pouvoir spécifier le port ouvert (autre que celui par défaut)

#### pom.xml client

Créer un pom.xml pour créer un éxécutable client, qui puisse se connecter à un serveur distant ou local

#### Executable projet

Créer un éxécutable du projet pour qu'il lance le serveur + 4 clients qui se connectent au serveur

#### Ajouter l'attribut plateau au serveur

Tache de l'user story #49

Ajouter un attribut plateau sur le serveur et l'initialiser.

#### Synchroniser la connexion et l'acceptation des joueurs

Tache de #52

Synchroniser les méthodes pour que l'accès aux ressources se bloquent quand utilisés

#### FaceMessage, plusieurs faces

Changer l'attribut Face en ArrayList car un joueur peut acheter plusieurs faces lors d'une forge

Modifier le code en conséquence

### Sous tache

#### Répondre au message de la deuxième action

Sous tâche de #58

Quand le client reçoit la demande du serveur s'il souhaite rejouer une action après sa première action, le client doit renvoyer un message au serveur contenant un boolean true-oui/false-non pour dépenser ses 2 pierres solaire et jouer une nouvelle

#### Calculer les statistiques pour chaque joueur

Sous tâche de #56

Calculer la moyenne de score, cartes achetés etc... de chaque joueurs sur toutes les parties joués.
Qui a le plus gagné, perdu, ...

Puis afficher les résultats dans la console

#### Récolter et stocker les chiffres d'une partie

Sous tâche de la tâche #55

Récolter les chiffres de la partie (Score de chaque joueur, qui est le gagnant, le nombre de cartes achetés par chaque joueur) et les stocker
Attention à la mémoire, pour le stockage il faut que le programme puisse éxécuter et stocker les résultats d'une centaine de parties

#### Traiter la réponse de la 2ème action

Sous tâche de #59

Quand le serveur reçoit la réponse du client pour la deuxième action.

Si la réponse est false -> Prochain tour

Si la réponse est true -> On retire 2 pierres solaires au joueur dans le plateau et on envoie un message à tous les joueurs pour les notifier d'enlever 2 pierres au joueur.
Ensuite le serveur envoie un message pour désigner le joueur qui doit jouer (le joueur qui vient de dépenser 2 pierres solaire) et attend l'action qu'il choisit de faire.

À la fin de sa deuxième action, on passe le tour pour que le joueur suivant puisse jouer.

#### ClientManager extends Joueur

Sous tâche de #50

Modifier la classe ClientManager pour qu'elle extend de Joueur.

#### Mettre à jour le plateau du serveur en fonction des événements

Sous tâche de #49

Mettre à jour le plateau du serveur en fonction des événements du jeu et des actions des joueurs.

#### Set des ClientManager sur le plateau

Sous tache de #61

Ajouter les ClientManager sur le plateau (ClientManager extends de Joueur donc il n'y aura pas de problèmes)

Vérifier que les ClientManager dans la classe Server pointe vers la même adresse mémoire (hashCode) que les ClientManager qui sont dans le plateau du GameManager qui leurs sont associés.
Il faut que les 2 objets pointent vers la même adresse mémoire pour partager les modifications (modification de l'inventaire etc...)

## Itération 5
### User story

#### Activer les effets
En tant que joueur je souhaite pouvoir activer les effets de mes cartes afin d'obtenir un avantage en jeu.

Critère d'acceptance :

*Les cartes ont les bons effets associer
*Le joueur peut activer les effets des cartes si c'est l'effet de la carte est activable à chaque tour
*L'effet de la carte s'active lors de son achat, si son effet s'active uniquement à l'achat

#### Effet des cartes 

En tant que joueur je souhaite pouvoir activer les effets de mes cartes afin de modifier le cours du jeu

Critère d'acceptance :

*Toutes les cartes du jeu doivent possèder leurs effets

#### Statistique

En tant que statisticien, je souhaite obtenir les statistiques des résultats de plusieurs parties lancées d'affilés, afin de pouvoir déterminer qui est le meilleur joueur.

### Tâche

#### Executer plusieurs parties d'affilés

Tâche de l'user story #53

Executer plusieurs parties d'affilés avec les mêmes joueurs

#### Création de l'effet de la carte minotaure

Tâche de #67

Effet : Tous les autres joueurs lancent leurs dés, les placent
sur leur emplacement et appliquent leurs pouvoirs
avec les modifications suivantes :

    Toutes les faces qui rapportent des ressources (VictoryPoint compris) en font perdre à la place.

NB : Si une réserve est à 0 (ou l’atteint), alors toute perte
supplémentaire de cette ressource est sans effet.
NB 2 : Si un joueur a le choix de la ressource qu’il perd,
il peut choisir une ressource dont il ne dispose pas ou
en quantité insuffisante (pour minimiser ses pertes.)

#### Création de l'effet de la cartes Satyres

Tâche de #67

Effet : Tout les autres joueurs lancent leurs dés, les replacent sur leur emplacement et n'appliquent pas les pouvoirs affichés.
Puis vous désignez 2 faces parmi celles affichées sur les dés adverses et en appliquez les pouvoirs, comme si vous receviez la faveur des dieux.

#### Création de l'effet de la carte SilverHindCard

Tâche du User Story #67

Effet : recevez une faveur mineure

#### Création de l'effet de la carte SphinxCard

Tache de #67
Recevez une faveur mineure 4 fois de suite.
(Vous devez utiliser le même dé pour les 4 lancers.)

#### Création de l'effet de la carte Marteau du Forgeron

Tâche de #67

Effet : Retournez cette carte et placez-la
dans l’emplacement prévu au-dessus de votre Inventaire de
Héros. Prenez un jeton Marteau et placez-le côté « I » sur l’icône marteau
de votre parcours.
Désormais, à chaque fois que vous obtenez de l’or par un quelconque
moyen, vous pouvez décider de ne pas tout ajouter à votre réserve et utiliser tout ou partie de cet or pour avancer votre jeton Marteau d’autant de cases sur le parcours.

Si le jeton Marteau atteint la dernière case "1gold" , recevez 10 VictoryPoint , retournez-le côté « II » et placez-le sur l’icône pour continuer directement le parcours.
Si le jeton Marteau atteint la dernière case "1gold" à nouveau, recevez
15 VictoryPoint , placez votre carte « Marteau » sur votre pile de cartes sans effet permanent et placez le jeton Marteau à côté.

Obtenir une autre carte « Marteau » avant d’avoir défaussé les précédentes :

    Placez la carte « Marteau » temporairement sous les premières, en attendant que celles-ci soient terminées.
    Lorsque vous terminez le parcours « II » en cours, vous pouvez continuer
    directement un parcours « I » sur la carte « Marteau » suivante.
    Vous pouvez garder le même jeton Marteau pour vos différents parcours.

#### Création de l'effet de la carte Coffre du Forgeron

Tâche de #67

Effet : Prenez une tuile Coffre et assemblez-la à la suite de votre Inventaire de Héros, augmentant ainsi les limites
de vos réserves.

#### Création de l'effet de la carte Mirroir Abyssal

Tâche de #67

Effet : Récupérez une face "portail" des Jardins du Temple et forgez-la aussitôt sur un de vos dés.

#### Création de l'effet de la carte CancerCard

Tache de #67
Recevez les faveurs des dieux deux fois de suite

#### Création de l'effet de la carte Casque d'invisibilité

Tâche de #67

Effet : Récupérez une face "x3" des Jardins du Temple et forgez-la aussitôt sur un de vos dés.

#### Création de l'effet de la carte l'Ancien

Tâche de #67

Pas d’effet immédiat.
Effet : vous pouvez dépenser 3 Gold pour gagner 4 VictoryPoint .

#### Création de l'effet de la carte Les Ailes de la Gardienne

Tâche de #67

Effet : Pas d’effet immédiat.
Effet : recevez 1 gold , 1 solary ou 1 lunary.

#### Faire activer les effets du bot

Tache de l'user story #92

Implémenter les fonctions nécessaires pour que le joueur puisse activer l'effet des cartes qui sont activables à chaque tour.

#### Création de l'effet de la carte WildSpiritCard

Tache de #67
Recevez 3 gold et 3 lunary.

### Sous-Tâche

#### Activation de l'effet de la carte

Sous tache de la tache #93

Durant son tour si le Joueur possède des cartes avec le boolean en pour savoir si l effet est activable à chaque tour en true. Le joueur peut envoyer un CardEffectMessage au serveur avec le nom de la carte qu'il a activé pour que l'effet se déclenche.

À la fin de son tour, le boolean de toutes les cartes qui indique si la carte a été activé ou pas repassent à true.

#### Selection de la ressource sur les faces OU

Lorsque qu'un roll d'un dé tombe sur une face "cette ressource ou cette ressource" bloquer l'éxécution de la partie et envoyez un message au client pour lui dire de selectionner la ressource.

Une fois le choix de la ressource communiqué envoie des résultats des rolls à tous les clients.

Dans les faces OU ne garder que la ressource selectionnée

#### Ajout des points du marteau

Sous tache de #78

Quand le marteau atteint un pallier de gold et qu'il donne des points de victoire au joueur ayant activé le marteau,

Ajouter les points dans le plateau du serveur puis envoyer un rollMessage avec le nom du joueur + une face avec les points que le marteau lui a donné

#### Placer une partie des golds reçus dans le marteau

Sous tache de #78

Quand le joueur obtient des golds apres son role de dés, s'il possède la carte marteau du forgeron lui envoyer une demande s'il souhaite en placer une partie dans le marteau

#### Ajouter la possibilité d'augmenter la limite de ressource de l'inventaire

Sous tâche de #75

Ajouter des fonctions pour augmenter la limite de ressource de l'inventaire pour créer l'effet de la carte Coffre du forgeron

#### Boolean carte activable à chaque tour

Sous tache de la tache #93

Ajouter un boolean à la classe Card et aux cartes qui extends de la classe pour savoir si l'effet de la carte est activable à chaque tour ou non.

True -> Oui l'effet est activable à chaque tour
False -> Non, l'effet ne se déclenche qu'à son achat

Ajouter un autre boolean pour savoir si la carte a été activé ou non durant le tour
True -> La carte n'a pas été activé ce tour ci et peut être activer.
False -> La carte n'est pas activable OU la carte a déjà été activé

#### Envoyer une demande au client de la face qu'il souhaite copier

Sous tache de #71

Quand le roll des dés du joueur tombe sur le mirroir, le serveur attend avant d'envoyer les rollMessage, le serveur envoie une demande au joueur qui est tombé sur le mirroir avec dans la demande les faces des autres qu'ils sont tombés, le joueur doit choisir parmis ces faces, la face qu'il souhaite copier et la renvoyer au serveur.

Une fois la réponse reçu le serveur remplace le résultat miroir par la face sélectionné et envoie les rollMessage

#### Création d'une CardEffectFactoryServer

Sous tâche de #67

Créer une CardEffectFactory à intégrer dans le listener du Serveur.

On donne le nom de la carte à la factory, et elle s'occupe d'appliquer les effets de la carte sur le Plateau ou de faire les roll & d'envoyer les messages

Cette factory sera différente de celle du Client, car elle pourra servir aux cartes qui ont comme effet de faire roll les dés. Comme le serveur est le seul système capable de lancer les dés des joueurs.

#### Création d'une CardEffectMessage

Sous tache de #67

Créer un message qui communiquera le nom de la carte dont l'effet a été activé par un des Client.

Le nom de cette carte sera donnée à une CardEffectFactory pour éxécuter les effets de la carte

#### Créer le mirroir

Sous tache de #71

Créer un mirroir message avec une ArrayList de face qui contiendra les faces des autres joueurs qui sont tombés lors du lancer et un attribut Face faceSelected qui sera null à l'envoi par le serveur mais qui sera rempli par le joueur avec une des faces contenus dans l'arraylist

#### Lancer de dé de dieu mineures

Sous tache de #77

Modification de la méthode lancerDeMineur pour qu'elle prenne en paramètre l'index du dé souhaité

## Itération 6

### User story

#### Comportement des bots

En tant que joueur, je veux pouvoir jouer une partie avec des bots de plusieurs difficultés. Certains bots auront des comportements autre que purement aléatoire.

#### Effet des cartes

En tant que joueur je souhaite pouvoir activer les effets de mes cartes afin de modifier le cours du jeu

Critère d'acceptance :

Toutes les cartes du jeu doivent possèder leurs effets

### Tache

#### Executer plusieurs parties d'affilés

Tâche de l'user story #53

Executer plusieurs parties d'affilés avec les mêmes joueurs

#### Creation des bots

Tache de #96

Création des différents types de bots du jeu.

#### Création de l'effet de la carte SilverHindCard

Tâche du User Story #67

Effet : recevez une faveur mineure

#### Création de l'effet de la carte SphinxCard

Tache de #67
Recevez une faveur mineure 4 fois de suite.
(Vous devez utiliser le même dé pour les 4 lancers.)

#### Creation de l'effet de la carte minotaure

Tâche de #67

Effet : Tous les autres joueurs lancent leurs dés, les placent
sur leur emplacement et appliquent leurs pouvoirs
avec les modifications suivantes :

Toutes les faces qui rapportent des ressources (VictoryPoint compris) en font perdre à la place.
NB : Si une réserve est à 0 (ou l’atteint), alors toute perte
supplémentaire de cette ressource est sans effet.
NB 2 : Si un joueur a le choix de la ressource qu’il perd,
il peut choisir une ressource dont il ne dispose pas ou
en quantité insuffisante (pour minimiser ses pertes.)

#### Creation de l'effet de la carte WildSpiritCard

Tache de #67
Recevez 3 gold et 3 lunary.

### Sous tache

#### Création du bot rentable

Sous tache de #97

Création du bot qui fait des actions en fonction de son nombre de ressources. Il prend toujours le dé ou la face qui lui rapporte le plus de ressources. Lors de la forge il prend la face la plus cher qu'il puisse acheter. Pareil pour les cartes.

### Autre

#### Reprendre la javadoc

Relire toutes la javadoc, lui apporter des corrections pour qu'elle soit en accord avec les modifications faites au code. Et la rajouter dans les méthodes et les classes qui n'en ont pas

#### Mise à jours des statistiques

Améliorer les statistiques avec les ajouts des différents effets de cartes

#### Reprendre les tests unitaires

Perfectionner les tests unitaires déjà présents et en rajouter

## Itération 7

### User Story

#### Effet des cartes

En tant que joueur je souhaite pouvoir activer les effets de mes cartes afin de modifier le cours du jeu

Critère d'acceptance :

Toutes les cartes du jeu doivent possèder leurs effets

#### Comportement des bots

En tant que joueur, je veux pouvoir jouer une partie avec des bots de plusieurs difficultés. Certains bots auront des comportements autre que purement aléatoire.

### Tache

#### Mettre à jour les effets des cartes

Tache de #67
Mettre a jour les effets des cartes pour prendre en compte les mirroir.

#### Tuer le thread

Tuer le thread en fin de partie pour pouvoir relancer un mvn exec

#### Création des bots

Tache de #96

Création des différents types de bots du jeu.

### Sous tache

#### Création du botCard

Sous tache de #97
Création du BotCard qui dès qu'il peut acheter une carte il le fait sinon il forge des faces qui donne soit des solary soit des lunary. S'il peut pas acheter de solary ou lunary il prend la face qui donne le plus de gold.

#### Création du botVictoryPoint

Sous tache de #97
Création du BotVictoryPoint qui achete des cartes avec le maximum de victoryPoint il le fait sinon il forge des faces qui donne des faces avec des victoryPoint. S'il ne peut pas acheter de victoryPoint il prend la face qui donne le plus de lunary ou de solary et sinon le plus de gold.

### Autre

#### Reprendre la javadoc

Relire toutes la javadoc, lui apporter des corrections pour qu'elle soit en accord avec les modifications faites au code. Et la rajouter dans les méthodes et les classes qui n'en ont pas

#### Reprendre les tests unitaires

Perfectionner les tests unitaires déjà présents et en rajouter

#### Amélioration des logs

Améliorer les logs du projet et faire apparaître les effets.

#### Mise à jours des statistiques

Améliorer les statistiques avec les ajouts des différents effets de cartes