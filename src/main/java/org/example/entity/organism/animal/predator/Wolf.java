package org.example.entity.organism.animal.predator;

import org.example.settings.BaseOrganismSettings;

public class Wolf extends Predator {
    public Wolf() {
        super(new BaseOrganismSettings(Wolf.class.getSimpleName()));
    }

}
