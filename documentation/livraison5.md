# Rendu de la cinquième itération
## Fonctionnalités implémentées
* Création d'une CardEffectFactory pour le serveur
* Création des effets des cartes dans la CardEffectFactory
* Création de messages divers pour utiliser les effets des cartes
* Ajout du marteau du forgeron dans les statistiques

## Tâches réalisées
* Améliorer la javadoc
* Améliorer les tests via l'ajout de Mockito
* Création d'une ServerCardEffectFactory
* Création des effets de toutes les cartes
* Envoie et réception de nouveaux messages en lien avec les effets des cartes
* Ajout des golds dans le marteau du forgeron dans les statistiques
* Séparation du projet en plusieurs modules avec chacun leurs pom.xml

## Difficulté rencontré
Nous avons rencontré des difficultés pour le mirroir, en effet quand le joueur tombe sur un mirroir, on doit lui demander quelle face il souhaite copier. La difficulté vient que tout le système socket.io est asynchrone alors que la nous aurions besoin que cela soit synchrone afin de récupérer la face désirée et continuer le jeu avec la face choisie.

Nous avons eu le meme problème avec les faces de type OU, où le joueur doit choisir une des ressources de la face. Nous avons besoin de l'action du client mais de manière synchrone alors que socket.io est asynchrone.

Nous devons donc travailler sur ces difficultés, sur un système nous permettant de récupérer ces actions du client et qu'ensuite nous puissions continuer le jeu.

Des difficultés ont encore été rencontré sur les statistiques qui s'améliorent d'itérations en itérations mais cela est plus compliqué qu'imaginer. De même pour jouer plusieurs parties d'affilés.

## Solutions apportés
Aucune solution aux deux premiers problèmes n'ont été apporté comme nous travaillons encore activement dessus. Donc certaines issues des cartes seront remises dans la prochaine itération que nous terminions de travailler dessus.

Pour le problème des statistiques et de plusieurs parties d'affilés, nous allons nous remettre les tâches dans notre prochaine itération.

## Bilan sur les tests
Pour la partie nous avons ajouté Mockito mais nous ne l'avons pas encore utilisé, nous avons été fort occupé par la création des effets des cartes et les divers problèmes que cela à engendrer.

Nous avons rajouté des tests pour la classe Inventaire, Card et De