package org.example.entity.organism.animal.herbivore;

import org.example.settings.BaseOrganismSettings;

public class Caterpillar extends Herbivore {
    public Caterpillar() {
        super(new BaseOrganismSettings(Caterpillar.class.getSimpleName()));
    }
}
