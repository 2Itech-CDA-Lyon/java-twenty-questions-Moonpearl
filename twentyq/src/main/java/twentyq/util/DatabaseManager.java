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
        return manager.createQuery("SELECT question FROM Question question WHERE question.parentQuestion IS NULL", Question.class)
        .getSingleResult();
    }
}
