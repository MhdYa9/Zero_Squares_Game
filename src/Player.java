
import structure.Game;

public abstract class Player {

    Game game;

    public abstract void play();

    public Player(){
        this.game = new Game();
    }
}
