package org.example.entity.organism.animal.herbivore;

import org.example.settings.BaseOrganismSettings;

public class Sheep extends Herbivore{
    public Sheep() {
        super(new BaseOrganismSettings(Sheep.class.getSimpleName()));
    }
}
