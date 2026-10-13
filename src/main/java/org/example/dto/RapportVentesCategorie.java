package org.example.dto;

import java.math.BigDecimal;

public class RapportVentesCategorie {
    private final Long categorieId;
    private final String categorieNom;
    private final BigDecimal chiffreAffaires;

    public RapportVentesCategorie(Long categorieId, String categorieNom, BigDecimal chiffreAffaires) {
        this.categorieId = categorieId;
        this.categorieNom = categorieNom;
        this.chiffreAffaires = chiffreAffaires;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public String getCategorieNom() {
        return categorieNom;
    }

    public BigDecimal getChiffreAffaires() {
        return chiffreAffaires;
    }
}
