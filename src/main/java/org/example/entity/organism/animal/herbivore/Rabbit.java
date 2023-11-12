package org.example.entity.organism.animal.herbivore;

import org.example.settings.BaseOrganismSettings;

public class Rabbit extends Herbivore {
    public Rabbit() {
        super(new BaseOrganismSettings(Rabbit.class.getSimpleName()));
    }
}
