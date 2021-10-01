package twentyq;

import twentyq.game.Game;
/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws Exception {
        
        // Crée une nouvelle partie
        Game game = new Game();

        // Efface la console
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

        // Tant que le jeu est actif
        while ( game.isRunning() ) {
            // Met le jour à jour à chaque cycle d'exécution
            game.update();
        }

        // Arrête l'application avec un code de succès
        System.exit(0);

    }
}
