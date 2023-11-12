package org.example.entity.organism.animal.predator;

import org.example.settings.BaseOrganismSettings;

public class Eagle extends Predator {
    public Eagle() {
        super(new BaseOrganismSettings(Eagle.class.getSimpleName()));
    }
}
