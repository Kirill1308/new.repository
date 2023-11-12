package org.example.entity.organism.animal.herbivore;

import org.example.settings.BaseOrganismSettings;

public class Buffalo extends Herbivore{
    public Buffalo() {
        super(new BaseOrganismSettings(Buffalo.class.getSimpleName()));
    }
}
