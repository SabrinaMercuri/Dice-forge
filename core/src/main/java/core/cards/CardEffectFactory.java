package core.cards;

/**
 * Classe répertoriant les différents effets des cartes
 */
public abstract class CardEffectFactory {

	/**
	 * Méthode permettant d'identifier l'effet de la carte selon le nom 
	 * et d'appliquer l'effet au bon joueur
	 * @param cardName, le nom de la carte dont on veut l'effet
	 * @param namePlayer, le joueur jouant la carte
	 */
	public void factory(String cardName, String namePlayer) {
		switch (cardName) {
		case Card.BLACKSMITHCHEST:
			blacksmithChestEffect(namePlayer);
			break;
		case Card.BLACKSMITHHAMMER:
			blacksmithHammerEffect(namePlayer);
			break;
		case Card.ABYSSALMIRROR:
			abyssalMirrorEffect(namePlayer);
			break;
		case Card.CANCER:
			cancerEffect(namePlayer);
			break;
		case Card.WILDSPIRIT:
			wildSpiritEffect(namePlayer);
			break;
		case Card.SPHINX:
			sphinxEffect(namePlayer);
			break;
		case Card.ELDER:
			elderEffect(namePlayer);
			break;
		case Card.GUARDIANOWLS:
			guardianOwlsEffect(namePlayer);
			break;
		case Card.INVISIBILITYHELMET:
			invisibilityHelmetEffect(namePlayer);
			break;
		case Card.MINOTAUR:
			minotaurEffect(namePlayer);
			break;
		case Card.SATYRS:
			satyrsEffect(namePlayer);
			break;
		case Card.SILVERHIND:
			silverHindEffect(namePlayer);
			break;
		default:
			System.err.println("Cette carte n'a pas d'effet ou n'existe pas ! [" + cardName + "]");
			break;
		}
	}

	/**
	 * Méthodes abstraites permettant d'activer les effets des cartes
	 * @param namePlayer, nom du joueur jouant la carte
	 */
	public abstract void blacksmithChestEffect(String namePlayer);
	public abstract void blacksmithHammerEffect(String namePlayer);
	public abstract void abyssalMirrorEffect(String namePlayer);
	public abstract void cancerEffect(String namePlayer);
	public abstract void wildSpiritEffect(String namePlayer);
	public abstract void sphinxEffect(String namePlayer);
	public abstract void elderEffect(String namePlayer);
	public abstract void guardianOwlsEffect(String namePlayer);
	public abstract void invisibilityHelmetEffect(String namePlayer);
	public abstract void minotaurEffect(String namePlayer);
	public abstract void satyrsEffect(String namePlayer);
	public abstract void silverHindEffect(String namePlayer);

}
