package twentyq.util;

import javax.persistence.*;

import twentyq.entity.*;

/**
 * Service qui centralise toutes les opérations en base de données
 */
final public class DatabaseManager
{
    /**
     * L'unique instance de cette classe qui existe à tout moment
     */
    static private DatabaseManager instance;
    /**
     * Le gestionnaire d'entité qui va permettre de communiquer avec la base de données de manière effective
     */
    private EntityManager manager;

    /**
     * Créer un nouveau gestionnaire de base de données
     */
    private DatabaseManager()
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("TwentyQuestions");
        manager = factory.createEntityManager();
    }

    /**
     * Récupère l'unique instance de cette classe qui existe à tout moment
     * @return
     */
    static public DatabaseManager getInstance()
    {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Trouve la toute première question de l'arbre (la seule question sans parent)
     * @return
     */
    public Question findFirstQuestion()
    {
        return manager.createQuery("SELECT question FROM Question question WHERE parentQuestion IS NULL", Question.class)
            .getSingleResult();
    }

    /**
     * Récupère la question suivante dans l'arbre
     * @param currentQuestion La question actuelle
     * @param response La réponse à la question actuelle
     * @return
     */
    public Question findNextQuestion(Question currentQuestion, boolean response)
    {
        try {
            return manager.createQuery("SELECT question FROM Question question WHERE parentQuestion = :parentQuestion AND parentQuestionAnswer = :parentQuestionAnswer", Question.class)
                .setParameter("parentQuestion", currentQuestion)
                .setParameter("parentQuestionAnswer", response)
                .getSingleResult();
        }
        catch (NoResultException exception) {
            return null;
        }
    }

    /**
     * Récupère la solution associée à une question
     * @param currentQuestion La question concernée
     * @param response La réponse à la question concernée
     * @return
     */
    public Solution findSolutionByQuestion(Question currentQuestion, boolean response)
    {
        try {
            return manager.createQuery("SELECT solution FROM Solution solution WHERE parentQuestion = :parentQuestion AND parentQuestionAnswer = :parentQuestionAnswer", Solution.class)
                .setParameter("parentQuestion", currentQuestion)
                .setParameter("parentQuestionAnswer", response)
                .getSingleResult();
        }
        catch (NoResultException exception) {
            return null;
        }
    }

    public void save(Question question)
    {
        manager.getTransaction().begin();
        if (question.getId() == null) {
            manager.persist(question);
        } else {
            manager.merge(question);
        }
        manager.getTransaction().commit();
    }

    public void save(Solution solution)
    {
        manager.getTransaction().begin();
        if (solution.getId() == null) {
            manager.persist(solution);
        } else {
            manager.merge(solution);
        }
        manager.getTransaction().commit();
    }
}
