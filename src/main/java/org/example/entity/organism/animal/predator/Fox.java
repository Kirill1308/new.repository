package org.example.entity.organism.animal.predator;

import org.example.settings.BaseOrganismSettings;

public class Fox extends Predator {
    public Fox() {
        super(new BaseOrganismSettings(Fox.class.getSimpleName()));
    }
}
