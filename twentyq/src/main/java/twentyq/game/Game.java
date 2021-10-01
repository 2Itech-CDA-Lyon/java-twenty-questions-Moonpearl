package twentyq.game;

import java.util.Scanner;

import javax.persistence.NoResultException;
import javax.xml.crypto.Data;

import javassist.bytecode.stackmap.BasicBlock.Catch;
import twentyq.entity.Question;
import twentyq.entity.Solution;
import twentyq.util.Console;
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
    private Scanner scanner;

    /**
     * Crée une nouvelle partie
     */
    public Game()
    {
        // Indique que la partie est en cours d'exécution
        isRunning = true;
        // Récupère la première question de l'arbre
        currentQuestion = DatabaseManager.getInstance().findFirstQuestion();
        scanner = new Scanner(System.in);
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
        
        // Demande à l'utilisateur de répondre à la question par oui ou par non
        boolean response = Console.getInstance().askYesOrNo();
        try {
            // Cherche s'il existe une solution associée au prochain noeud de l'arbre
            Solution currentSolution = DatabaseManager.getInstance().findSolutionByQuestion(currentQuestion, response);
            System.out.println(String.format("Je pense que votre animal est: %s", currentSolution.getName()));

            if (Console.getInstance().askYesOrNo()) {
                System.out.println("J'ai trouvé! :)");
            } else {
                System.out.println("Je donne ma langue au chat! :(");
            }
        }
        catch (NoResultException exception) { }
        // Récupère la question suivante dans l'arbre en se basant sur la réponse de l'utilisateur
        try {
            currentQuestion = DatabaseManager.getInstance().findNextQuestion(currentQuestion, response);
        }
        catch (NoResultException exception) {
            terminate();
        }
    }
}
