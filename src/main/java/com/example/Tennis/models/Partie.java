package com.example.Tennis.models;

import java.util.*;

public class Partie {
    Joueur joueur1 = new Joueur("toto");
    Joueur joueur2 = new Joueur("titi"); ;
    private List<String> scoreHistory;
    private final List<String> tennisScores = new ArrayList<String>(Arrays.asList("0", "15", "30", "40"));
    private int jeuxJoueur1 = 0;
    private int jeuxJoueur2 = 0;
    private Map<String, Integer> scores;
    public Partie(String joueur1, String joueur2)
    {

        this.joueur1.nom = joueur1;
        this.joueur2.nom = joueur2;

        scores = new LinkedHashMap<String, Integer>();
        scores.put(joueur1, 0);
        scores.put(joueur2, 0);

        scoreHistory = new ArrayList<String>(Arrays.asList("0-0"));
    }
    public Partie()
    {

    }
    public boolean finDeJeux()
    {
        return (scores.get(joueur1.nom) > 3 || scores.get(joueur2.nom) > 3) && (Math.abs(scores.get(joueur1.nom) - scores.get(joueur2.nom)) > 1);
    }
    private boolean egalite()
    {
        return scores.get(joueur1.nom) > 2 && scores.get(joueur2.nom) > 2 && !finDeJeux();
    }

    public String reportScore()
    {
        if (finDeJeux())
        {
            if (scores.get(joueur1.nom) > scores.get(joueur2.nom))
            {

                return joueur1.nom + " gagne";

            }
            else
            {
                return joueur2.nom + " gagne";
            }
        }else if (egalite())
        {
            if (scores.get(joueur1.nom) > scores.get(joueur2.nom))
            {
                return "avantage " + joueur1.nom + "\njeux décisif";
            }
            else if (scores.get(joueur1.nom) < scores.get(joueur2.nom))
            {
                return "avantage " + joueur2.nom + "\njeux décisif";
            }
            else
            {
                return "egalite";
            }
        } else
        {
            return tennisScores.get(scores.get(joueur1.nom)) + "-" + tennisScores.get(scores.get(joueur2.nom));
        }
    }
    public void incrementScore(String playerName)
    {
        if (finDeJeux())
        {
            System.out.println("le jeux est terminé");
        }
        else
        {
            scores.put(playerName, scores.get(playerName) + 1);
            scoreHistory.add(reportScore());
            System.out.println(scoreHistory);
        }
    }
    public String getScoreHistory()
    {
        StringBuilder historyString = new StringBuilder();
        for (String score : scoreHistory)
        {
            historyString.append(score + "\n");
        }
        // Cuts off trailing '\n'
        return historyString.toString().substring(0, historyString.length() - 1);
    }

}
