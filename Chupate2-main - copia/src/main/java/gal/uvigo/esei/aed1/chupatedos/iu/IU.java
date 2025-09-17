package gal.uvigo.esei.aed1.chupatedos.iu;
import java.util.Scanner;

import gal.uvigo.esei.aed1.chupatedos.core.DeckOfCards;

/**
 * La clase interfaz de usuario (IU) representa al intermediario (se aplica abstracción) 
 * de la partida, entre la lógica interna (implementación) y los jugadores.
 * Se encarga de mostrar mensajes por pantalla y de recibir información.
 * @author Chupate2_AE
 */
public class IU {
    private final Scanner keyboard;

    /**
     * Constructor de la clase interfaz de usuario (IU).
     */
    public IU() {
        keyboard = new Scanner(System.in);
    }

    /**
     * Muestra el mensaje recibido como parámetro de entrada por pantalla.
     * @param msg mensaje que se mostrará por pantalla.
     */
    public void displaymessage(String msg) {
        System.out.println(msg);
    }

    /**
     * Muestra por pantalla el mensaje recibido como parámetro
     * @param message mensaje que se quiere mostrar
     * @return el número recogido
     * @throws NumberFormatException si el número que se introduce no cumple con el formato
     */
    public int readInt(String menssage) throws NumberFormatException {
        int res = 0;
        boolean error = false;

        do {
            error = false;
            System.out.println(menssage);
            
            try {
                res = Integer.parseInt(keyboard.nextLine());
            } catch (NumberFormatException e) {
                error = true;
            }
        
        } while (error);
        
        return res;
    }

    /**
     * Muestra por pantalla la ristra de caracteres recibidos como parámetro y
     * devuelve la respuesta recibida correspondiente.
     * @param msg mensaje que se quiere mostrar por pantalla
     * @return repuesta (toReturn) correspondiente al mensaje mostrado (msg)
     */
    public String readString(String msg) {
        String toReturn;

        System.out.print(msg);
        toReturn = keyboard.nextLine();
        
        return toReturn;
    }

    /**
     * Solicita la cantidad de jugadores que desean jugar entre dos a 
     * cinco (ambos incluídos).
     * También solicita sus nombres y los añade al array de jugadores (jugadores).
     * @return Array de jugadores (jugadores)
     */
    public String[] readPlayers() {
        int cantidad = 0;
        
        do {
            cantidad = this.readInt("Introduzca el numero de jugadores (min: 2 / max: 5): ");
        } while (cantidad < 2 || cantidad > 5);
        
        String[] jugadores = new String[cantidad];

        for (int i = 0; i < cantidad; i++) {
            do {
                jugadores[i] = readString("Nombre del jugador " + i + ": "); 
            } while (jugadores[i].trim() == "");

            for (int j = 0; j<i;j++){
                if (jugadores[i].equals(jugadores[j])){
                    jugadores[i] += " (";
                    jugadores[i] += i+1;
                    jugadores[i] += ")";
                }
            }
        }

        return jugadores;
    }

      /**
     * Solicita la cantidad de jugadores que desean jugar entre dos a 
     * cinco (ambos incluídos).
     * También solicita sus nombres y los añade al array de jugadores (jugadores).
     * @return Array de jugadores (jugadores)
     * @param cantidad de jugadores posibles dependiendo de las cartas
     */
    public String[] readPlayers(int cantidadDeMazos) {
        int cantidad = 0;
        int cantidadMaxJug = Math.floorDiv(cantidadDeMazos*40,7);

        do {
            cantidad = this.readInt("Introduzca el numero de jugadores (min: 2 / max: " + cantidadMaxJug + "): ");
        } while (cantidad < 2 || cantidad > cantidadMaxJug);
        
        String[] jugadores = new String[cantidad];

        for (int i = 0; i < cantidad; i++) {
            do {
                jugadores[i] = readString("Nombre del jugador " + i + ": "); 
            } while (jugadores[i].trim() == "");

            for (int j = 0; j<i;j++){
                if (jugadores[i].equals(jugadores[j])){
                    jugadores[i] += " (";
                    jugadores[i] += i+1;
                    jugadores[i] += ")";
                }
            }
        }

        return jugadores;
    }

    

    /**
     * Crea una baraja de cartas (DeckOfCards).
     * @return baraja de cartas (DeckOfCards).
     */
    public DeckOfCards createDeckOfCards() {
        return new DeckOfCards();
    }

    /**
     * Crea una baraja de cartas (DeckOfCards) vacía (por defecto).
     * @return baraja de cartas (DeckOfCards) vacía.
     */
    public DeckOfCards createEmptyDeckOfCards() {
        DeckOfCards emptyDeck = new DeckOfCards();
        
        while (!emptyDeck.isEmpty())
            emptyDeck.pop();
        
        return emptyDeck;
    }

}
