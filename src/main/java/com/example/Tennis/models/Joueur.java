package com.example.Tennis.models;

public class Joueur {
    String nom;

    public Joueur(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
