package gal.uvigo.esei.aed1.chupatedos.core;
import java.util.Stack;

/**
 * La clase mesa (Table) representa al lugar donde se colocará la baraja de cartas (DeckOfCards).
 * @author Chupate2_AE
 */
public class Table {
    private Stack<Card> descartes;
    
    /**
     * Constructor de la clase mesa (Table)
     * Inicializa una pila de cartas (descartes).
     */
    public Table() {
        this.descartes = new Stack<Card>();
    }
    
    /**
     * Devuelve y elimina la última carta añadida a la pila (descartes).
     * @return carta (Card), la última añadida a la pila (descartes)
     */
    public Card popCard(){
        return this.descartes.pop();
    }

    /**
     * Comprueba si la carta (Card) recibida es nula o no.
     * Si lo es, devuelve una excepción.
     * En caso contrario, la añade a la pila (descartes).
     * @param card carta (Card) que se comprobará si es o no nula.
     */
    public void receiveCard(Card card){
        if(card == null)
            throw new IllegalArgumentException("La carta no puede ser nula");
        
        this.descartes.push(card);
    }

    /**
     * Devuelve la carta (Card) en la cima de la baraja de la mesa.
     *  @return carta (Card) en la cima de la baraja de la mesa.
     */
    public Card getTopCard() {
        if (descartes.isEmpty())
            throw new IllegalStateException("No hay carta en la mesa");

        return descartes.peek();
    }

    /**
     * Devuelve el número de cartas de la pila (descartes).
     * @return número de cartas de la pila (descartes)
     */
    public int sizeDescartes() {
        return this.descartes.size();
    }
}
