package gal.uvigo.esei.aed1.chupatedos.core;
import java.util.ArrayList;

/**
 * La clase jugador (Player) representa a un jugador.
 * @author Chupate2_AE
 */
public class Player {
    private String name;
    private final ArrayList<Card> cards;

    /**
     * Constructor de la clase jugador (Player).
     * Inicializa a un jugador (Player) con un nombre y una lista de cartas vacía asociados.
     * @param name nombre del jugador (Player)
     */
    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    /**
     * Devuelve el nombre del jugador (Player).
     * @return nombre (name) del jugador (Player)
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del jugador (Player) según el parámetro recibido.
     * @param name nombre del jugador (Player)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve las cartas (cards) del jugador (Player).
     * @return
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Añade cartas (Card) a la lista de cartas (cards) del jugador (Player)
     * @param card carta que se añadirá a la lista (cards)
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Elimina la carta (Card) recibida como parámetro de la lista de cartas (cards) del jugador (Player)
     * @param card carta (Card) que será eliminada de la lista (cards)
     * @return verdadero (true) si se eliminó la carta con éxito o falso (false) en caso contrario.
     */
    public boolean removeCard(Card card){
        return this.cards.remove(card);
    }

    /**
     * Devuelve las cartas permitidas con las que jugar el jugador (Player), 
     * en base al número recibido en una determinada ronda de la partida.
     * @param tableCard carta (Card) en la cima de la baraja de la mesa.
     * @return las cartas candidatas (candidateCards) con las que poder jugar.
     */
    public ArrayList<Card> availableCardsToPlayByNumber(Card tableCard) {
        ArrayList<Card> candidateCards = new ArrayList<>();

        for (Card card : cards)
            if (card.getNumber() == tableCard.getNumber())
                candidateCards.add(card);

        return candidateCards;
    }

    /**
     * Devuelve las cartas permitidas con las que jugar el jugador (Player), 
     * que coincidan con el palo recibido en una determinada ronda de la partida.
     * @param tableCard carta (Card) en la cima de la baraja de la mesa.
     * @return las cartas candidatas (candidateCards) con las que poder jugar.
     */
    public ArrayList<Card> availableCardsToPlayBySuit(Card tableCard) {
        ArrayList<Card> candidateCards = new ArrayList<>();

        for (Card card : cards)
            if (card.getSuit() == tableCard.getSuit())
                candidateCards.add(card);

        return candidateCards;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(", tiene las cartas \n ________________ \n");
        
        for (Card card : this.cards){
            sb.append(card);
            sb.append("\n");
        }
        
        sb.deleteCharAt(sb.length()-1);
        sb.append("\n ---------------- ");
        
        return sb.toString();
    }
}
