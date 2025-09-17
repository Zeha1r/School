
package gal.uvigo.esei.aed1.chupatedos.iu;
import gal.uvigo.esei.aed1.chupatedos.core.Game;

/**
 * La clase principal (Main) representa el punto de inicio de la partida.
 * Se encarga de iniciar el juego en si.
 * @author Chupate2_AE
 */
public class Main {
  
  /**
   * Inicializa la interfaz de usuario (IU), el propio juego y lo pone en marcha.
   * @param args argumentos que no son utilizados en ningún momento.
   */
  public static void main(String[] args) {
    IU iu = new IU();
    Game chupateDos = new Game(iu);
    chupateDos.play();
  }
}
