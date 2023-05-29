package com.example.formation;

public class Formation {
    private String id;
    private String titre;
    private String description;
    private String nbr_heure;
    private String date;
    private String categorie;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNbr_heure() {
        return nbr_heure;
    }

    public void setNbr_heure(String nbr_heure) {
        this.nbr_heure = nbr_heure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
