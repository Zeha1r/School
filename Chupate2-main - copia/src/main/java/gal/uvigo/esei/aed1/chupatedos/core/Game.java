package gal.uvigo.esei.aed1.chupatedos.core;
import java.util.ArrayList;
import java.util.stream.Collectors;

import gal.uvigo.esei.aed1.chupatedos.iu.IU;

/**
 * La clase juego (Game) simula una partida.
 * @author Chupate2_AE
 */
public class Game {
    private final IU iu;
    private Player[] players;
    private int numOfPlayers;
    private final Table table;
    private DeckOfCards deck;
    private int currentPlayer;
    private boolean antiHorario;
    private boolean seHaGanado;
    private int cantidadMazos;

    /**
     * Constructor de la clase juego (Game).  
     * Inicializa las variables de la interfaz de usuario (IU).  
     * @param iu interfaz de usuario que se inicializará  
     */  
    public Game(IU iu) {
        
        this.currentPlayer = 0;
        this.seHaGanado = false;
        this.antiHorario = false;
        this.iu = iu;
        this.cantidadMazos = iu.readInt("Cantidad de mazos deseados:");
        this.deck = new DeckOfCards(cantidadMazos);
        this.table = new Table();
    }

    /**
     * Crea todos los jugadores y los asigna al array de jugadores (players).
     */
    private void crearJugadores() {
        String[] allPlayers = iu.readPlayers(this.cantidadMazos);
        this.numOfPlayers = allPlayers.length;
        this.players = new Player[this.numOfPlayers];
        
        for (int i = 0; i < this.numOfPlayers; i++)
            this.players[i] = new Player(allPlayers[i]);
    }

    /**
     * Invoca al método de mezclar de la clase baraja de cartas (DeckOfCards)
     * quien las barajará.
     */
    private void shuffleDeck(){
        this.deck.shuffleDeck();
    }

    /**
     * Muestra el estado de la mesa.
     * Es decir, la carta en la cima de la mesa y las cartas que aún hay en la baraja,
     * el jugador que debe jugar y su siguiente.
     */
    private void estadoMesa() {
        iu.displaymessage("Carta en la cima " + this.table.getTopCard());
        iu.displaymessage("|Quedan " + this.deck.size() + " Cartas en la baraja|");
        iu.displaymessage("|Hay " + this.table.sizeDescartes() + " Cartas en Descartes|");
        iu.displaymessage("Es el turno de " + this.players[this.currentPlayer]);
        iu.displaymessage("el siguiente jugador es "
                + this.players[this.siguienteJugador()].getName());
    }

    /**
     * Devuelve las cartas que tiene permitido usar el jugador (Player).
     * @param card carta (Card) en base a la que podrá jugar
     * @return cartas que puede elegir jugar
     */
    private ArrayList<Card> availableCards(Card card) {
        ArrayList<Card> possibleCardsToUse = this.players[this.currentPlayer].availableCardsToPlayByNumber(card);
        possibleCardsToUse.addAll(this.players[this.currentPlayer].availableCardsToPlayBySuit(card));

        possibleCardsToUse = (ArrayList<Card>) possibleCardsToUse.stream().distinct().collect(Collectors.toList());
        
        return possibleCardsToUse;
    }

    /**
     * Calcula el siguiente jugador.
     * @return índice del siguiente jugador que tiene turno del array de jugadores (players)
     */
    private int siguienteJugador(){
        if (antiHorario){
            if (this.currentPlayer + 1 >= this.numOfPlayers) return 0;
            return this.currentPlayer + 1;
        }

        if (this.currentPlayer - 1 < 0) return this.numOfPlayers - 1;
        return this.currentPlayer - 1;
    }
    
    /**
     * Devuelve la carta en la cima de la baraja (pila).
     * Si ya no se pueden robar más cartas de la baraja (pila), 
     * excepto la carta que está en la cima de la baraja de la mesa, 
     * se mezclan y se retiran de la mesa, devolviéndolas a la baraja de 
     * cartas (deck, de DeckOfCards).
     */
    private void emptyDeck(){
        if (this.deck.isEmpty()){
            Card tableCard = this.table.popCard();

            for (int i = 0; i < this.table.sizeDescartes(); i++)
                this.deck.addCard(this.table.popCard());

//            this.deck.shuffleDeck();
            this.table.receiveCard(tableCard);
        }
    }


    /**
     * Devuelve la carta en la cima de la baraja (pila).
     * Si ya no se pueden robar más cartas de la baraja (pila), 
     * excepto la carta que está en la cima de la baraja de la mesa, 
     * se mezclan y se retiran de la mesa, devolviéndolas a la baraja de 
     * cartas (deck, de DeckOfCards).
     * @return carta (Card) en la cima de la baraja (pila)
     */
    private Card robarCarta(){
       Card aRobar = null;
        this.emptyDeck();
        aRobar = this.deck.pop();
        if (aRobar == null){
            throw new NullPointerException("La carta que robo un jugador es Null, O por un mazo mal llenado o un Descartes con Null");
        }

        return aRobar;
    }

    

    /**
     * En base a la carta pasada como parámetro, se termina la partida,
     * se cambia de sentido o el siguiente jugador es obligado a coger
     * dos cartas y transferirle el turno al siguiente.
     * @param carta
     */
    private void jugarCarta(Card carta) {
        this.table.receiveCard(carta);
        this.players[this.currentPlayer].removeCard(carta);
        
        if (this.players[this.currentPlayer].getCards().isEmpty())
            this.seHaGanado = true;
        else if (carta.getNumber() == 7) 
            this.antiHorario = !this.antiHorario;
        else if (carta.getNumber() == 2) {
            this.currentPlayer = this.siguienteJugador();
            this.players[this.currentPlayer].addCard(robarCarta());
            this.players[this.currentPlayer].addCard(robarCarta());
            this.currentPlayer = this.siguienteJugador();
        }
    }

    /**
     * Se encarga del juego.
     * Es decir, invoca los métodos que crean los jugadores y mezclan la baraja de cartas (DeckOfCards).
     * Se le asignan las cartas a los jugadores.
     * Si la carta jugada es siete, se cambia de sentido.
     * Si la carta jugada es dos, se obliga a coger dos cartas al siguiente jugador y se omite, es decir, pierde turno.
     * Se comprueba si se ha ganado o no. Antes se muestra el estado de la mesa.
     * Si aún tiene cartas con las que jugar, muestra las disponibles y solicita que el jugador elija.
     * Si no tiene cartas permitidas, roba una de la baraja (pila) y se comprueba si es aceptable.
     * Si lo es, la juega. En caso contrario, pierde el turno.
     * Si se ha ganado, la partida termina y se muestra el nombre del ganador.
     */
    public void play() {
        this.crearJugadores();
//        this.shuffleDeck();
        
        for (int i = 0; i < this.numOfPlayers * 7; i++)
            this.players[i % numOfPlayers].addCard(this.deck.pop());
        emptyDeck();
        this.table.receiveCard(this.deck.pop());
        iu.displaymessage("\n =======\tPrimer Turno\t=======");

        switch (this.table.getTopCard().getNumber()) {
            case 7:
                this.antiHorario = true;
                this.currentPlayer = this.numOfPlayers-1;
                iu.displaymessage("Empezamos invertidos");
                break;
            case 2:
                this.players[this.currentPlayer].addCard(robarCarta());
                this.players[this.currentPlayer].addCard(robarCarta());
                this.currentPlayer = this.siguienteJugador();
                iu.displaymessage("Empezamos con un 2 ");
                break;
            case 4:
                this.players[this.currentPlayer].addCard(robarCarta());
                this.players[this.currentPlayer].addCard(robarCarta());
                this.players[this.currentPlayer].addCard(robarCarta());
                this.players[this.currentPlayer].addCard(robarCarta());
                this.currentPlayer = this.siguienteJugador();
                iu.displaymessage("Empezamos con un 4 ");
                break;
            case 12:
                 this.currentPlayer = this.siguienteJugador();
                iu.displaymessage("Empezamos con un 12 ");
                break;
            default:
                break;
        }
        
        while (!seHaGanado){
            this.estadoMesa();
            ArrayList<Card> cartasDisponibles = this.availableCards(this.table.getTopCard());
            
            if (!cartasDisponibles.isEmpty()){
                iu.displaymessage("Que carta quieres jugar? ");
                int opcion = 0;
                
                do{
                    for (int i = 0; i < cartasDisponibles.size();i++)
                        iu.displaymessage("\t " + i + ": " + cartasDisponibles.get(i));

                    opcion = iu.readInt("Introduce el numero: ");
                } while (opcion < 0 || opcion > cartasDisponibles.size() - 1);

                Card cartaEscogida = cartasDisponibles.get(opcion);
                this.jugarCarta(cartaEscogida);
            } else {
                Card nextCard = robarCarta();
                iu.displaymessage( "\nJugador " + this.players[currentPlayer].getName() + " no puedes jugar ninguna carta,  robar " + nextCard);
                
                if (nextCard.getSuit() == this.table.getTopCard().getSuit() || nextCard.getNumber() == this.table.getTopCard().getNumber()){
                    iu.displaymessage("Que suerte, puedes jugarla!");
                    this.jugarCarta(nextCard);
                }else{
                    this.players[this.currentPlayer].addCard(nextCard);
                    iu.displaymessage("No puedes jugar ninguna carta, pierdes el turno");
                }
            }
            
            if (!seHaGanado){this.currentPlayer = this.siguienteJugador();iu.displaymessage("\n =======\tSiguiente turno\t=======");}
        }
        
        iu.displaymessage("\n\nEl jugador: " + this.players[this.currentPlayer].getName() + " Ha ganado ");
    }
}
