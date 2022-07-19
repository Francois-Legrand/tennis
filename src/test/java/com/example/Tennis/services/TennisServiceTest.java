package com.example.Tennis.services;

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
    private final String joueur1 = "toto";
    private final String joueur2 = "titi";

    AutoCloseable closeable;
    private void advanceGame(int incrementServer, int incrementOpponent)
    {
        for (int i = 0; i < incrementServer; i++) partie.incrementScore(joueur1);
        for (int i = 0; i < incrementOpponent; i++) partie.incrementScore(joueur2);
    }

    @BeforeEach
    public void setup() throws IOException {
        closeable = MockitoAnnotations.openMocks(this);
        partie = new Partie(joueur1,joueur2);
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
        new Partie(joueur1,joueur2);
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
        partie.incrementScore(joueur1);
        assertEquals("15-0", partie.reportScore());

        partie.incrementScore(joueur2);
        assertEquals("15-15", partie.reportScore());

        advanceGame(2,0);
        assertEquals("40-15", partie.reportScore());
    }
    @Test
    @DisplayName("Devrait retourner l'historique des scrores ")
    public void retourneLhistoriqueDesScore()
    {
        for (int i = 0; i < 3; i++) {
            partie.incrementScore(joueur1);
            partie.incrementScore(joueur2);
        }
        assertEquals("0-0\n15-0\n15-15\n30-15\n30-30\n40-30\ndeuce", partie.getScoreHistory());

        partie.incrementScore(joueur1);
        assertEquals("0-0\n15-0\n15-15\n30-15\n30-30\n40-30\ndeuce\nadvantage " + joueur1,
                partie.getScoreHistory());

        partie.incrementScore(joueur2);
        assertEquals("0-0\n15-0\n15-15\n30-15\n30-30\n40-30\ndeuce\nadvantage " + joueur1 +"\ndeuce",
                partie.getScoreHistory());

        partie.incrementScore(joueur2);
        assertEquals("0-0\n15-0\n15-15\n30-15\n30-30\n40-30\ndeuce\nadvantage " + joueur1 +"\ndeuce" + "\nadvantage " + joueur2,
                partie.getScoreHistory());

        partie.incrementScore(joueur1);
        assertEquals(
                "0-0\n15-0\n15-15\n30-15\n30-30\n40-30\ndeuce\nadvantage " + joueur1 +"\ndeuce" + "\nadvantage " + joueur2 + "\ndeuce",
                partie.getScoreHistory());

        partie.incrementScore(joueur1);
        assertEquals(
                "0-0\n15-0\n15-15\n30-15\n30-30\n40-30\ndeuce\nadvantage " + joueur1 +"\ndeuce" + "\nadvantage " + joueur2 + "\ndeuce" + "\nadvantage " + joueur1,
                partie.getScoreHistory());

        partie.incrementScore(joueur1);
        assertEquals(
                "0-0\n15-0\n15-15\n30-15\n30-30\n40-30\ndeuce\nadvantage " + joueur1 +"\ndeuce" + "\nadvantage " + joueur2 + "\ndeuce" + "\nadvantage " + joueur1 + "\n" + joueur1 + " wins",
               partie.getScoreHistory());
    }
    @Test
    @DisplayName("Devrait retourner que le joueur 1 gagne")
    public void retourneSiLeJoueur1GagneApresUnePartie()
    {
        advanceGame(3,3);
        assertEquals("deuce", partie.reportScore());

        partie.incrementScore(joueur1);
        assertEquals("advantage " + joueur1, partie.reportScore());

        partie.incrementScore(joueur2);
        assertEquals("deuce", partie.reportScore());

        partie.incrementScore(joueur2);
        assertEquals("advantage " + joueur2, partie.reportScore());

        partie.incrementScore(joueur1);
        assertEquals("deuce", partie.reportScore());

        partie.incrementScore(joueur1);
        assertEquals("advantage " + joueur1,partie.reportScore());

        partie.incrementScore(joueur1);
        assertEquals(joueur1 + " wins", partie.reportScore());
    }
    @Test
    @DisplayName("Devrait retourner que la fonction finDeJeux fonctionne")
    public void verifieQueFinDeJeuxFonctionne()
    {
        advanceGame(3,3);
        assertEquals(false, partie.finDeJeux());

        partie.incrementScore(joueur1);
        assertEquals(false, partie.finDeJeux());

        partie.incrementScore(joueur1);
        assertEquals(true, partie.finDeJeux());
    }
    @Test
    @DisplayName("Devrait retourner que le joueur1 gagne")
    public void retourneLeJoueur1Gagnant()
    {
        advanceGame(4, 0);
        assertEquals(joueur1 + " wins", partie.reportScore());
    }

    @Test
    @DisplayName("Devrait retourner que le joueur2 gagne")
    public void retourneLeJoueur2Gagnant()
    {
        advanceGame(0,4);
        assertEquals(joueur2 + " wins", partie.reportScore());
    }
    @Test
    @DisplayName("Devrait retourner une egalité")
    public void retourneUneEgalite()
    {
        advanceGame(1,1);
        assertEquals("15-15", partie.reportScore());
    }
}
