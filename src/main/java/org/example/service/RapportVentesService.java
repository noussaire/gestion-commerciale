package org.example.service;

import org.example.dao.JPAUtil;
import org.example.dto.RapportVentesCategorie;
import org.example.entities.Categorie;
import org.example.entities.Commande;
import org.example.entities.CommandeProduit;
import org.example.entities.Produit;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class RapportVentesService {
    public List<RapportVentesCategorie> ventesDuMoisPrecedent() {
        YearMonth moisPrecedent = YearMonth.now().minusMonths(1);
        return ventesParCategorie(moisPrecedent.atDay(1), moisPrecedent.atEndOfMonth());
    }

    public List<RapportVentesCategorie> ventesParCategorie(LocalDate debut, LocalDate fin) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<RapportVentesCategorie> cq = cb.createQuery(RapportVentesCategorie.class);

            Root<CommandeProduit> ligne = cq.from(CommandeProduit.class);
            Join<CommandeProduit, Commande> commande = ligne.join("commande");
            Join<CommandeProduit, Produit> produit = ligne.join("produit");
            Join<Produit, Categorie> categorie = produit.join("categorie");

            Expression<BigDecimal> prix = produit.get("prix");
            Expression<BigDecimal> quantite = cb.toBigDecimal(ligne.get("quantite"));
            Expression<BigDecimal> totalLigne = cb.prod(prix, quantite);

            cq.select(cb.construct(
                    RapportVentesCategorie.class,
                    categorie.get("id"),
                    categorie.get("nom"),
                    cb.sum(totalLigne)
            ));
            cq.where(cb.between(commande.get("dateCommande"), debut, fin));
            cq.groupBy(categorie.get("id"), categorie.get("nom"));
            cq.orderBy(cb.asc(categorie.get("nom")));

            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
}
