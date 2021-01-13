package com.microservice.articlesservice.model;

import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@JsonFilter("monFiltreDynamique")
public class Article {

    @Id
    //@GeneratedValue
    private int id;
    private String nom;
    private int prix;

    private int prixAchat;

    public Article() {

    }

    public Article(int id, String nom, int prix, int prixAchat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixAchat = prixAchat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }

    public int getMarge() {
        return this.prix - this.prixAchat;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", nom=" + nom + "\'" +
                ", prix=" + prix + "}";
    }
}
