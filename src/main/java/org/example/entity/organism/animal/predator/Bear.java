package org.example.entity.organism.animal.predator;

import org.example.settings.BaseOrganismSettings;

public class Bear extends Predator {
    public Bear() {
        super(new BaseOrganismSettings(Bear.class.getSimpleName()));
    }
}
