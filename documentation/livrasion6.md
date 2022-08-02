# Rendu de la sixième itération
## Fonctionnalités implémentées
* Implémentation des faces miroirs
* Correction de certains effets implémentés précédament
* Implémentation de plusieurs partie
* Creation du bot rentable

## Tâches réalisées
* Améliorer la javadoc
* Améliorer les tests 
* Création du bot rentable
* Implémentation des MirrorFace
* exécuter plusieurs parties d'affilés

## Difficulté rencontré
Nous avons rencontré des difficultés notaments parfois,lors de la réception du ChooseRessourceMessage du client, le server ne le reçoit pas et que cela bloque le programme, ensuite il y a aussi l'affichage qui n'est pas encore synchroniser lorsque l'on fait plusieurs partie.

## Solutions apportés
Pour palier au problème de reception, une solution serait de mettre en place des Ack et vérifier que le serveur recoit bien le message sinon on le renvoie. 
Pour ce qui est du problème d'affichage, il suffira de le synchroniser lors de la prochaine itération. Donc certaines issues seront remises dans la prochaine itération que nous terminions de travailler dessus.

## Bilan sur les tests
Nous avons améliorer les tests pour les classes Joueur, StackCard, Isle. 
Suite aux remarques faites sur les classes de tests, nous avons mis au propre celle de Isle, Card, StackCard, Inventaire.

En ce qui concerne les Mockito nous ne l'avons pas encore utilisé, on a préféré se focaliser sur les taches de l'itération précédente.
