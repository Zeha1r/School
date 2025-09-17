package gal.uvigo.esei.aed1.chupatedos.core;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;

import es.uvigo.esei.aed1.tads.common.EmptyException;

/**
 * La clase baraja de cartas (DeckOfCards) simula una baraja de cartas e 
 * internamente actúa como una pila de enumerados (enum).
 * @author Chupate2_AE
 */
 public class DeckOfCards{
    private final Stack<Card> Cards;
    private boolean isShuffled;

    /**
     * Constructor de la clase baraja de cartas (DeckOfCards).
     * Inicializa el atributo isShuffled a falso (false), una pila de cartas (Card) vacía y 
     * llama a un método que la rellena (fillDeck()).
     */
    public DeckOfCards() {
        this.isShuffled = false;
        this.Cards = new Stack<Card>();
        this.fillDeck();
    }


    // Añadido para hacer multiples copias de las cartas y Tener dos de por ejemplo oros
    public DeckOfCards(int copiesOfDeck) {
        this.isShuffled = false;
        this.Cards = new Stack<Card>();
        for (int i = 0; i < copiesOfDeck; i++) {
            this.fillDeck();
        }
        
    }

    /**
     * Añade todas las cartas (Card) necesarias para la partida a la baraja (pila) de cartas.
     */
    public final void fillDeck() {
        int numCards = Card.values().length;

        for (int i = 0; i < numCards; i++)
            this.Cards.push(Card.values()[i]);

        this.isShuffled = false;
    }

    /**
     * Mezcla las cartas de la baraja (pila) y 
     * al terminar modifica el atributo isShuffled a verdadero (true).
     */
    public final void shuffleDeck() {
        Collections.shuffle(this.Cards);
        this.isShuffled = true;
    }

    /**
     * Elimina y devuelve (mostrándo) la carta (Card) en la cima de la baraja (pila).
     * @return carta (Card) en la cima de la baraja (pila)
     * @throws EmptyStackException si la baraja (pila) está vacía
     */
    public Card pop() {
        if (this.isEmpty())
            throw new EmptyStackException();
        return this.Cards.pop();
    }

    /**
     * Muestra la carta (Card) en la cima de la baraja (pila) evitando eliminarla de esta.
     * @return carta (Card) en la cima de la baraja (pila)
     * @throws EmptyStackException si la baraja (pila) está vacía.
     */
    public Card peek() {
        if (this.isEmpty()) 
            throw new EmptyException();
        return Cards.peek();
    }

    /**
     * Añade una carta a la cima de la baraja (pila) de cartas.
     * @param card carta (Card) que será añadida a la cima de la baraja (pila)
     */
    public void addCard(Card card) {
        this.Cards.push(card);
    }
    
    /**
     * Comprueba si la baraja (pila) de cartas está vacía o no.
     * @return verdadero (true) si la baraja (pila) está vacía o falso (false) en caso contrario
     */
    public boolean isEmpty() {
        return this.Cards.size() == 0;
    }

    /**
     * Devuelve la cantidad de cartas que se encuentran en la pila.
     * @return número de cartas en la pila.
     */
    public int size(){
        return this.Cards.size();
    }

    /**
     * Comprueba si se mezclaron las cartas de la baraja (pila).
     * @return verdadero (true) si se mezclaron las cartas o falso (false) en caso contrario
     */
    public boolean isShuffled() {
        return this.isShuffled;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        
        result = prime * result + ((Cards == null) ? 0 : Cards.hashCode());
        result = prime * result + (isShuffled ? 1231 : 1237);
        
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        DeckOfCards other = (DeckOfCards) obj;
        
        if (Cards == null)
            if (other.Cards != null) return false;

        else if (!Cards.equals(other.Cards)) return false;

        if (isShuffled != other.isShuffled) return false;
            
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DeckOfCards: ")
                .append(this.isShuffled ? "is shuffled " : "is not shuffled ");
        sb.append(this.Cards);
        return sb.toString();
    }
}
