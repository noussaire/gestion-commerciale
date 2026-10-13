package org.example;

import org.example.entities.Categorie;
import org.example.entities.Produit;
import org.example.service.CrudService;
import org.example.service.ProduitService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProduitDaoTest {
    private static CrudService<Categorie, Long> catService;
    private static ProduitService prodService;

    @BeforeClass
    public static void init() {
        catService = new CrudService<>(Categorie.class);
        prodService = new ProduitService();
    }

    @Test
    public void testCreateProduitAvecCategorie() {
        String suffix = String.valueOf(System.currentTimeMillis());
        Categorie cat = new Categorie("Papeterie-" + suffix);
        catService.create(cat);

        Produit produit = new Produit("Stylo-" + suffix, new BigDecimal("1.20"), cat);
        prodService.create(produit);

        assertNotNull(produit.getId());
        Produit fetched = prodService.findByIdWithCategorie(produit.getId());
        assertEquals(cat.getNom(), fetched.getCategorie().getNom());
    }
}
