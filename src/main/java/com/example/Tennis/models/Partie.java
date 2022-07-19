package com.example.Tennis.models;

import java.util.*;

public class Partie {
    String joueur1; // serv
    String joueur2; // oppon
    private List<String> scoreHistory;
    private final List<String> tennisScores = new ArrayList<String>(Arrays.asList("0", "15", "30", "40"));
    private Map<String, Integer> scores;
    public Partie(String joueur1, String joueur2)
    {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;

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
        return (scores.get(joueur1) > 3 || scores.get(joueur2) > 3) && (Math.abs(scores.get(joueur1) - scores.get(joueur2)) > 1);
    }
    private boolean egalite()
    {
        return scores.get(joueur1) > 2 && scores.get(joueur2) > 2 && !finDeJeux();
    }
    public String reportScore()
    {
        if (finDeJeux())
        {
            if (scores.get(joueur1) > scores.get(joueur2))
            {

                return joueur1 + " wins";
            }
            else
            {
                return joueur2 + " wins";
            }
        }
        else if (egalite())
        {
            if (scores.get(joueur1) > scores.get(joueur2))
            {
                return "advantage " + joueur1;
            }
            else if (scores.get(joueur1) < scores.get(joueur2))
            {
                return "advantage " + joueur2;
            }
            else
            {
                return "deuce";
            }
        }
        else
        {
            return tennisScores.get(scores.get(joueur1)) + "-" + tennisScores.get(scores.get(joueur2));
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
