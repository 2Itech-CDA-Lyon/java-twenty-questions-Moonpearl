package twentyq.game;

import java.util.Scanner;

import javax.persistence.NoResultException;

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

                // Demande à l'utilisateur quelle était sa solution
                Solution userSolution = new Solution();
                System.out.println("À quel animal pensiez-vous?");
                userSolution.setName(
                    Console.getInstance().askInput()
                );

                // Demande à l'utilisateur de créer une nouvelle question permettant de discriminer entre les deux solutions
                Question userQuestion = new Question();
                System.out.println(String.format("Quelle question me permettrait de faire la différence entre %s et %s?", currentSolution.getName(), userSolution.getName()));
                userQuestion.setText(
                    Console.getInstance().askInput()
                );
                // La nouvelle question proposée par l'utilisateur devient la question que l'on atteint lorsque que l'on répond à la dernière question posée par le jeu
                // par la dernière réponse qui a été donnée
                userQuestion.setParentQuestion(currentQuestion);
                userQuestion.setParentQuestionAnswer(response);

                // Demande à l'utilisateur quelle réponse à sa question mêne vers sa solution
                System.out.println(String.format("Si je vous pose la question: %s, la réponse %s correspond-elle à (O)ui ou (N)on?", userQuestion.getText(), userSolution.getName()));
                boolean userResponse = Console.getInstance().askYesOrNo();
                // La nouvelle solution proposée par l'utilisateur devient la solution que l'on atteint lorsque l'on répond à la nouvelle question
                // par la réponse que l'utilisateur vient de donner
                userSolution.setParentQuestion(userQuestion);
                userSolution.setParentQuestionAnswer(userResponse);

                // La solution proposée par le jeu devient la solution que l'on atteint lorsque l'on répond à la nouvelle question
                // par l'inverse de la réponse que l'utilisateur vient de donner
                currentSolution.setParentQuestion(userQuestion);
                currentSolution.setParentQuestionAnswer(!userResponse);

                // Sauvegarde les modifications en base de données
                DatabaseManager manager = DatabaseManager.getInstance();
                manager.save(userQuestion);
                manager.save(userSolution);
                manager.save(currentSolution);

                System.out.println();
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
