
package players;
import structure.Game;

import java.util.Scanner;

public abstract class Player {

    public final Scanner input = new Scanner(System.in);

    public abstract void play(Game game);

}
