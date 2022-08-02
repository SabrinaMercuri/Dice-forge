# Rendu de la trosième itération
## Fonctionnalités implémentées
Nous avons implementé les fonctionnalités suivantes :
* Gérer la forge
* Création des îles avec leurs piles de carte
* Faire jouer les bot (On lui fait faire des actions aléatoires pour le moment il choisit toujours aléatoirement une action et dans cette action il choisit aléatoirement ce qu'il achète en fonction des ressources qu'il possède)

## Tâches réalisées
L'ensemble des tâches et sous-tâches fixées pour cette deuxième itérations a été réalisé.
* Chasser le joueur si on achète une carte sur une île sur laquelle un joueur se trouve déjà
* Mise à jour des cartes
* Mise à jour des faces
* Création des piles de carte sur les îles
* Creation des faces à ressources multiple
* Creation des catégories dans la forge (Catégorie par cout de la face)
* Mise à jour des faces disponibles dans la forge
* Mise à jour de la modification du dè des joueurs
* Achat de Face par les joueurs
* Achat de carte par les joueurs
* Création des cartes du jeu (sans les effets qui seront fait dans une itération future)
* Création des piles de cartes
* Mise à jour des cartes disponibles
* Mise à jour des cartes possédès par les joueurs

## Difficulté rencontré
Nous avons rencontré des difficulté pour serialiser les objets qui contenaient des java.util.map et des java.util.list.

## Solutions apportés
Pour solutionner cette difficulté nous avons ajouter la dépendance "Jackson" qui permet de serialiser et deserialiser plus facilement les map et les list.


## Bilan sur les tests
Nous avons ajouté des classes test pour les classes que nous avons du ajouté pour les cartes et la forge.
Nous avons peaufiner les tests qui étaient déjà existant, car nous avons du mettre à jour des méthodes qui étaient dans les tests.
