# Rendu de la sixième itération
## Fonctionnalités implémentées
* Rework du roll des dés et du traitement des résultats
* Création de nouveaux bots (rentable, victory, card)
* Mode stat & log
* Arret du serveur à la fin de toutes les parties
* Argument pour le numéro de partie à éxécuter
* Rework effet des cartes

## Tâches réalisées
* Mettre à jour les effets des cartes
* Bots (victory, card, rentable)
* Tuer le thread
* Test unitaire
* Javadoc

## Difficulté rencontré
Notre ancien algorithme pour les roll et les résultats ne nous permettait que le client puisse choisir ses faces dans le cas d'un portail ou d'une face OU.

## Solutions apportés
Un rework complet des roll a du être fait divisant celui ci en plusieurs messages aller-retour entre le client et le serveur

## Bilan sur les tests
Des tests ont été rajouté pour la plupart des classes.
Difficulté à tester les parties du serveur, du au besoin omniprésent du réseau et de la connexion de clients dans le serveur. Une grosse partie du serveur n'a pas pu être tester
