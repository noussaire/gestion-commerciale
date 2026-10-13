package org.example.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_commande")
    private LocalDate dateCommande;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommandeProduit> lignes = new ArrayList<>();

    public Commande() {
        this.dateCommande = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public List<CommandeProduit> getLignes() {
        return Collections.unmodifiableList(lignes);
    }

    public void addProduit(Produit produit, int quantite) {
        CommandeProduit ligne = new CommandeProduit(this, produit, quantite);
        lignes.add(ligne);
        produit.getLignesCommande().add(ligne);
    }

    public void removeProduit(Produit produit) {
        lignes.removeIf(ligne -> {
            boolean match = ligne.getProduit().equals(produit);
            if (match) {
                produit.getLignesCommande().remove(ligne);
                ligne.setCommande(null);
                ligne.setProduit(null);
            }
            return match;
        });
    }
}
