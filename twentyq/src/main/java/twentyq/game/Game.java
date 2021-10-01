package twentyq.game;

import java.util.Scanner;

import twentyq.entity.Question;
import twentyq.util.DatabaseManager;

/**
 * Représente une partie du jeu
 */
public final class Game
{
    /**
     * Le jeu est-il en cours d'exécution?
     */
    private Boolean isRunning;
    /**
     * La question actuelle
     */
    private Question currentQuestion;

    /**
     * Crée une nouvelle partie
     */
    public Game()
    {
        // Indique que la partie est en cours d'exécution
        isRunning = true;
        // Récupère la première question de l'arbre
        currentQuestion = DatabaseManager.getInstance().findFirstQuestion();
    }

    /**
     * Le jeu est-il en cours d'exécution?
     * @return
     */
    public Boolean isRunning()
    {
        return isRunning;
    }

    /**
     * Arrête le jeu
     */
    public void terminate()
    {
        isRunning = false;
    }

    /**
     * Décrit un cycle d'exécution du jeu
     */
    public void update()
    {
        // Affiche le texte de la question actuelle
        System.out.println(currentQuestion.getText());
        Scanner scanner = new Scanner(System.in);
        
        // Demande à l'utilisateur de répondre à la question
        boolean valid = false;
        do {
            // Attend une saisie utilisateur
            String userInput = scanner.nextLine().toUpperCase();

            // Valide la saisie utilisateur
            if (userInput.equals("O") || userInput.equals("N")) {
                valid = true;
            } else {
                System.out.println("Vous devez répondre par (O)ui ou par (N)on!");
            }
        // Recommence tant que la saisie n'est pas valide
        } while(!valid);
        
        scanner.close();
        terminate();
    }
}
