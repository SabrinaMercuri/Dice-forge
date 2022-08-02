# Rendu de la trosième itération
## Fonctionnalités implémentées
* Ajout d'un plateau dans le serveur pour que lui aussi ait sa version du jeu
* Synchronisation Client/Serveur, attend les interactions du client
* Ajout des statistiques
* Jouer plusieurs parties d'affilés

## Tâches réalisées
Les tâches que nous avons réalisé sont
* Ajout d'un plateau dans le serveur pour que lui aussi ait sa version du jeu
* Synchronisation Client/Serveur, attend les interactions du client
* Ajout des statistiques
* Jouer plusieurs parties d'affilés
* Création de plusieurs éxécutables .jar pour le client, serveur et pour le projet

## Difficulté rencontré
Nous avons rencontré des difficultés dans la synchronisation Serveur/Client c.a.d que le Serveur attende la réponse du client quand il le sollicite par exemple pour qu'il joue son tour.
Nous avons manqué de temps pour finaliser le lancement de plusieurs parties d'affilés du fait à une difficulté imprévue, nous terminerons la fonctionnalité dans la prochaine itération.

## Solutions apportés
La solution apportée à ce problème est après avoir solliciter le client, le serveur ne fait plus rien et c'est le message du client qui va venir débloquer le serveur car dans le listener du message a été une fonction qui fait poursuivre l'éxécution du jeu

## Bilan sur les tests
Des tests sur le Client ont été rajouté pour tester s'il marche correctement avec les actions