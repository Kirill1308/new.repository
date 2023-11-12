package org.example.entity.organism.animal.herbivore;

import org.example.settings.BaseOrganismSettings;

public class Horse extends Herbivore {
    public Horse() {
        super(new BaseOrganismSettings(Horse.class.getSimpleName()));
    }
}
