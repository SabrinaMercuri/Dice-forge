# User Story

## Titre : Lancer un dés
### Description :
En tant que joueur,
je souhaite pouvoir lancer les deux dés de mon inventaire 
afin de pouvoir obtenir des ressources.

### Tests d'acceptances
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

## Titre : Acheter des faces de dés
### Description :
En tant que joueur je peux changer la face d'un de mes dés afin de l'améliorer.

### Test d'acceptances :
Scenario :
Etant donné que je veuille forger un dé, si j'ai assez de ressources pour 
avoir l'amélioration choisie 
Alors je sélectionne la face de mon dé que je veux changer, puis la nouvelle face de mon dé.
La nouvelle face prend la place de l'ancienne.

## Titre : Acheter des cartes
### Description :
En tant que joueur, 
je peux acheter des cartes afin de pouvoir 
améliorer mon équipement ou gagner des points de victoire.

### Test d'acceptances 
Scénario : 
Etant donné que j'ai réuni assez de ressources
je peux acheter une carte.
Je l'obtiens.

## Titre : Dépenser 2 cristaux solaire pour rejouer
### Description
En tant que joueur,
je souhaite pouvoir dépenser 2 cristaux à la fin de mon tour pour pouvoir effectuer une nouvelle action

Note : On ne peut dépenser 2 cristaux solaire pour effectuer une nouvelle action qu'une seule fois par tour

### Critère d'acceptance :

Scénario :
J'ai dépensé tous mes golds pour acheter des faces de dès. Mon action prend fin, j'ai 2 cristaux solaires. On me propose de les dépenser pour pouvoir effectuer une nouvelle action.

Scénario :
J'ai fini mon action, je n'ai pas 2 cristaux solaires dans mon inventaire. Mon tour prend fin.

## Titre : Chasser les joueurs
### Description :
En tant que joueur,
Je souhaite chasser un joueur
Afin de me positionner sur une île 

### Tests d'acceptances :
Scénario :
Etant donné que j'ai acheté une carte
Et que je me déplace vers une île où un joueur est présent
Alors je le chasse

Scénario :
Etant donné que j'ai acheté une carte
Et que je me déplace vers une île déserte
Alors je me place dessus sans rien faire


## Titre : Choisir un dé pour faveur des dieux mineure
### Description 
En tant que joueur,
je souhaite pouvoir lancer un dé grâce à la faveur des dieux mineure quand l'effet de certaines cartes le permet afin d'obtenir une ressource.

### Critère d'acceptance :

Scénario :
Etant donné que j'ai activé ma faveur des dieux mineure 
Et que j'ai obtenu une ressource
Quand je veux la récupérer
Alors je l'ajoute à mon inventaire

Scénario :
Etant donné que j'ai activé ma faveur des dieux mineure 
Et que j'ai obtenu un multiplicateur
Quand je veux lle récupérer
Alors rien ne se passe

Scénario :
Etant donné que j'ai activé ma faveur des dieux mineure 
Et que j'ai obtenu un portail
Quand je veux la récupérer
Alors je choisis la ressource d'un adversaire et l'ajoute à mon inventaire


## Titre : Faire lancer les dès
### Description
En tant que serveur, je souhaite que lorsque le tour d'un joueur commence que tout les dès de tous les joueurs se lancent afin qu'ils puissent récupèreer des ressources à chacun des tours.

Note : Les dès de TOUS les joueurs se lancent à TOUS les tours.

### Critère d'acceptance : 
Lancer les dès des joueurs au début de tous les tours et que les ressources des dès leurs soient attribuées.


## Titre : Lancer les sorts des cartes
### Description
En tant que joueur, je souhaite que lorsque c'est mon tour que je puisse déclencher les effets spéciaux de mes cartes afin de profiter de leurs bonus

Note : Un effet spécial d'une carte ne peut être lancer que lors du tour du joueur possèdant la carte et qu'une seule fois par tour.

### Critère acceptance : 
Lorsque que le joueur déclenche l'effet de la carte, l'effet s'effectue bien.

Si ce n'est pas le tour du joueur et qu'il lance le sort rien ne doit se passer.

## Titre : Convertir les PV des cartes en Victory point en fin de partie
### Description :

En tant que serveur, je souhaite à la fin de la partie convertir les PV des cartes des joueurs pour leurs accorder des victory points supplémentaires afin de pouvoir établir le tableau des scores.

Note : 1 PV = 1 victory point

### Critère d'acceptance : 
Ajouter le total des PV des cartes au nombre de victory point déjà possèder par le joueur.

## Titre : Accepter les connexions de plusieurs clients
### Description 
En tant que serveur,
Je souhaite accepter les connexions de plusieurs clients
Afin de pouvoir lancer une partie 

### Tests d'acceptances :
Scénario :
Etant donné que j'ai pour objectif de lancer une partie
Et que pour lancé une partie il me faut entre 2 à 4 joueurs
Alors je vais accepter les différentes connexions de joueurs

Scénario :
Etant donné que ma partie est déjà composé de 4 joueurs
Et que pour lancé une partie il me faut entre 2 à 4 joueurs
Alors je refuse la connexion du joueur

Scénario :
Etant donné que ma partie est déjà en cours
Et qu'un joueur essaye de se connecter
Alors je lui refuse l'accès

## Titre : Attendre la connexion des autres joueurs pour que la partie soit pleine
### Description
En tant que serveur, je souhaite attendre la connexion de tous les joueurs avant de lancer la partie afin que la partie se joue avec 4 joueurs au maximum et 2 au minimum.

Note : Le jeu se joue entre 2 et 4 joueurs (3 inclus).

### Critère d'acceptance : 
Lancer la partie quand le nombre de 4 joueurs est atteint et notifier les joueurs du début de la partie.

Lorsqu'il y a moins de 2 joueurs, il ne faut pas lancer la partie

Lorsqu'il y a entre 2 et 3 joueurs on peut lancer la partie à partir d'un certain temps d'attente.

## Titre : Lancer la partie

### Description
En tant que serveur,
Je souhaite pouvoir lancer la partie immédiatement lorsque 4 joueurs sont connectés et la lancer après un délai lorsque 2 ou 3 joueurs sont connectés afin de pouvoir commencer à jouer.

### Critère d'acceptance :

Scénario :
Etant donné que 4 joueurs se sont connectés
Et qu'ils sont prêts
Quand je veux lancer la partie
Alors la partie se lance et une notification est envoyée aux joueurs pour les prévenir du début de partie.

Scénario :
Etant donné que 2 ou 3 joueurs se sont connectés
Et qu'ils sont prêts
Quand je veux lancer la partie
Alors la partie se lance après un délai et une notification est envoyée aux joueurs pour les prévenir du début de partie.

## Titre : Choisir un ordre de jeu des joueurs
### Description
En tant que serveur, je souhaite donner un ordre de tour aux joueurs, afin que que ceux-ci puissent jouer chacun à leurs tours.

### Critère d'acceptance :
Sur chaque manche faire joeur les 4 joueurs, puis à la prochaine manche refaire jouer les joueurs dans le même ordre

## Titre : Retransmettre les actions des autres joueurs
### Description
En tant que serveur,
Je souhaite pouvoir prévenir les autres joueurs des actions de chacun en tant réel afin qu'ils soient au courant du déroulement de la partie.

### Critère d'acceptance :
Scénario :
Etant donné qu'un joueur a effectué une action
Et que l'action est terminée
Quand je veux prévenir les autres joueurs
Alors j'envoi un message aux joueurs en détaillant l'action qui vient d'être effectué et qui l'a effectué.

Scénario :
Etant donné qu'un joueur n'a pas effectué d'action
Et que son tour est fini
Quand je veux prévenir les autres joueurs
Alors je ne fais rien et je passe au tour du joueur suivant.


## Titre : Fin du jeu
### Description 
En tant que serveur,
Je souhaite mettre un terme à la partie
Afin de pouvoir définir un gagnant

### Tests d'acceptances :
Scénario :
Etant donné que 9 tours sont passés
Et qu'ils sont terminés
Quand je veux terminer la partie
Alors je calcule les points et je défini un gagnant