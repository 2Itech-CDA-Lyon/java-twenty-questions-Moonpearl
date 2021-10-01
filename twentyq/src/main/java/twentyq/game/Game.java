package twentyq.game;

public final class Game {

    private Boolean isRunning;

    public Game()
    {
        isRunning = true;
    }

    public Boolean isRunning()
    {
        return isRunning;
    }

    public void terminate()
    {
        isRunning = false;
    }

    public void update()
    {
        System.out.println("Ça marche!");
        terminate();
    }
}
