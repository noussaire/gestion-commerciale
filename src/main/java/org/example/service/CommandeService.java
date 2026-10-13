package org.example.service;

import org.example.entities.Commande;

public class CommandeService extends CrudService<Commande, Long> {
    public CommandeService() {
        super(Commande.class);
    }
}
