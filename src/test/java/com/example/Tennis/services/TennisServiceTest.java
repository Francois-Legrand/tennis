package com.example.Tennis.services;

import com.example.Tennis.models.Joueur;
import com.example.Tennis.models.Partie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

public class TennisServiceTest {
    private Partie partie;
    private Joueur joueur1 = new Joueur("toto");
    private Joueur joueur2 = new Joueur("titi");

    AutoCloseable closeable;
    private void advanceGame(int j1, int j2)
    {

        for (int i = 0; i < j1; i++) partie.incrementScore(joueur1.toString());
        for (int i = 0; i < j2; i++) partie.incrementScore(joueur2.toString());
    }

    @BeforeEach
    public void setup() throws IOException {

        closeable = MockitoAnnotations.openMocks(this);
        partie = new Partie(joueur1.toString(),joueur2.toString());
    }
    @AfterEach
    public void closeMocks() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Devrait initialiser une partie")
    public void newGame() {
        new Partie();
    }
    @Test
    @DisplayName("Devrait ajouter deux joueurs")
    public void AjoutJoueurs() {
        new Partie(joueur1.toString(),joueur2.toString());
    }
    @Test
    @DisplayName("Devrait afficher le score de début de partie")
    public void ScoreDebutPartie() {

        assertEquals("0-0", partie.reportScore());
    }
    @Test
    @DisplayName("Devrait incrémenter les scrore ")
    public void incrémenterLesScoreDesJoueur()
    {
        partie.incrementScore(joueur1.toString());
        assertEquals("15-0", partie.reportScore());

        partie.incrementScore(joueur2.toString());
        assertEquals("15-15", partie.reportScore());

        advanceGame(2,0);
        assertEquals("40-15", partie.reportScore());
    }
    @Test
    @DisplayName("Devrait retourner l'historique des scrores ")
    public void retourneLhistoriqueDesScore()
    {
        for (int i = 0; i < 3; i++) {
            partie.incrementScore(joueur1.toString());
            partie.incrementScore(joueur2.toString());
        }
        assertEquals("0-0\n15-0\n15-15\n30-15\n30-30\n40-30\negalite", partie.getScoreHistory());

        partie.incrementScore(joueur1.toString());
        assertEquals("0-0\n15-0\n15-15\n30-15\n30-30\n40-30\negalite\navantage " + joueur1.toString()+"\njeux décisif",
                partie.getScoreHistory());

        partie.incrementScore(joueur2.toString());
        assertEquals("0-0\n15-0\n15-15\n30-15\n30-30\n40-30\negalite\navantage " + joueur1.toString() +"\njeux décisif"+"\negalite",
                partie.getScoreHistory());

        partie.incrementScore(joueur2.toString());
        assertEquals("0-0\n15-0\n15-15\n30-15\n30-30\n40-30\negalite\navantage " + joueur1.toString() + "\njeux décisif"+"\negalite" + "\navantage " + joueur2.toString()+"\njeux décisif",
                partie.getScoreHistory());

        partie.incrementScore(joueur1.toString());
        assertEquals(
                "0-0\n15-0\n15-15\n30-15\n30-30\n40-30\negalite\navantage " + joueur1.toString()+ "\njeux décisif"+"\negalite" + "\navantage " + joueur2.toString()+ "\njeux décisif"+ "\negalite",
                partie.getScoreHistory());

        partie.incrementScore(joueur1.toString());
        assertEquals(
                "0-0\n15-0\n15-15\n30-15\n30-30\n40-30\negalite\navantage " + joueur1.toString() +"\njeux décisif"+"\negalite" + "\navantage " + joueur2.toString() +"\njeux décisif"+ "\negalite" + "\navantage " + joueur1.toString()+ "\njeux décisif" ,
                partie.getScoreHistory());

        partie.incrementScore(joueur1.toString());
        assertEquals(
                "0-0\n15-0\n15-15\n30-15\n30-30\n40-30\negalite\navantage " + joueur1.toString()+"\njeux décisif" +"\negalite" + "\navantage " + joueur2.toString()+ "\njeux décisif"+ "\negalite" + "\navantage " + joueur1.toString()+ "\njeux décisif"+ "\n" + joueur1.toString() + " gagne, la partie et terminé",
               partie.getScoreHistory());
    }
    @Test
    @DisplayName("Devrait retourner que le joueur 1 gagne après une partie compliqué")
    public void retourneSiLeJoueur1GagneApresUnePartie()
    {
        advanceGame(3,3);
        assertEquals("egalite", partie.reportScore());

        partie.incrementScore(joueur1.toString());
        assertEquals("avantage " + joueur1+ "\njeux décisif", partie.reportScore());

        partie.incrementScore(joueur2.toString());
        assertEquals("egalite", partie.reportScore());

        partie.incrementScore(joueur2.toString());
        assertEquals("avantage " + joueur2 + "\njeux décisif", partie.reportScore());

        partie.incrementScore(joueur1.toString());
        assertEquals("egalite", partie.reportScore());

        partie.incrementScore(joueur1.toString());
        assertEquals("avantage " + joueur1+ "\njeux décisif",partie.reportScore());

        partie.incrementScore(joueur1.toString());
        assertEquals(joueur1 + " gagne, la partie et terminé", partie.reportScore());
    }
    @Test
    @DisplayName("Devrait retourner que la fonction finDeJeux fonctionne")
    public void verifieQueFinDeJeuxFonctionne()
    {
        advanceGame(3,3);
        assertEquals(false, partie.finDeJeux());

        partie.incrementScore(joueur1.toString());
        assertEquals(false, partie.finDeJeux());

        partie.incrementScore(joueur1.toString());
        assertEquals(true, partie.finDeJeux());
    }
    @Test
    @DisplayName("Devrait retourner que le joueur1 gagne et que la partie est terminé")
    public void retourneLeJoueur1Gagnant()
    {
        advanceGame(4, 0);
        assertEquals(joueur1.toString() + " gagne, la partie et terminé", partie.reportScore());
    }

    @Test
    @DisplayName("Devrait retourner que le joueur2 gagne et que la partie est terminé")
    public void retourneLeJoueur2Gagnant()
    {
        advanceGame(0,4);
        assertEquals(joueur2.toString() + " gagne, la partie et terminé", partie.reportScore());
    }
    @Test
    @DisplayName("Devrait retourner une egalité")
    public void retourneUneEgalite()
    {
        advanceGame(3,3);
        assertEquals("egalite", partie.reportScore());

    }
    @Test
    @DisplayName("Devrait retourner un avantage et un jeux décisif")
    public void retourneUnAvantage()
    {
        advanceGame(3,3);
        assertEquals("egalite", partie.reportScore());

        partie.incrementScore(joueur1.toString());
        assertEquals("avantage " + joueur1.toString()+ "\njeux décisif", partie.reportScore());

    }

}
