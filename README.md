# diceforge201920-tp2-dfa
diceforge201920-tp2-dfa created by GitHub Classroom

# Équipe

Sabrina Mercuri

Maxence Lefeuvre

Marco Gilles

Stéphane Desire

Alexis Camorani

# Install Maven

1. Télécharger Maven https://maven.apache.org/
2. Dézipper maven dans un repertoire (Repertoire systeme, peu importe)
3. Ajoutez les variables d'environnement dans les variables systèmes M2_HOME MAVEN_HOME qui pointe vers la où vous avez dézippé le projet maven
4. Si la variable d'environnement JAVA_HOME n'est pas déjà défini rajoutez la dans les variables système qui pointe vers votre Java JDK (ne pas pointez dans le bin, mais directement dans le répertoire)
5. Enjoy !

# Commande maven

* *Build :* mvn install -> Créer un .jar dans target/
* *Javadoc :* mvn site -> Créer un index.html dans target/site/apidocs
* *Execution :* mvn exec -> Execute le projet

# Installer et éxécuter le projet

## Pour éxécuter le serveur avec 4 clients
```
mvn install
mvn exec:java -pl lanceur -Dexec.args='NBPARTIE'
```
Le nombre de partie doit etre superieur ou egal a 1
* *Mode avec log :* NBPARTIE doit être égal à 1
* *Mode avec stats :* NBPARTIE doit être supérieur à 1

## Pour éxécuter le serveur
```
mvn install
mvn exec:java -pl server [-Dexec.args="..."]
```
L'option est [port]

## Pour éxécuter le client
```
mvn install
mvn exec:java -pl client [-Dexec.args="..."]
```
Les options sont [host] ou [host port]
