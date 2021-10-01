package twentyq.util;

import java.util.Scanner;

public final class Console
{
    static private Console instance;
    private Scanner scanner;

    private Console()
    {
        scanner = new Scanner(System.in);
    }

    static public Console getInstance()
    {
        if (instance == null) {
            instance = new Console();
        }
        return instance;
    }

    public String askInput()
    {
        return scanner.nextLine();
    }

    public boolean askYesOrNo()
    {
        boolean valid = false;
        String userInput;
        do {
            // Attend une saisie utilisateur
            userInput = scanner.nextLine().toUpperCase();
            // Valide la saisie utilisateur
            if (userInput.equals("O") || userInput.equals("N")) {
                valid = true;
            } else {
                System.out.println("Vous devez répondre par (O)ui ou par (N)on!");
            }
        // Recommence tant que la saisie n'est pas valide
        } while(!valid);

        return "O".equals(userInput);
    }
}
