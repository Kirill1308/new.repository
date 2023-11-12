package org.example.entity.organism.animal.predator;

import org.example.settings.BaseOrganismSettings;

public class Python extends Predator {
    public Python() {
        super(new BaseOrganismSettings(Python.class.getSimpleName()));
    }
}
