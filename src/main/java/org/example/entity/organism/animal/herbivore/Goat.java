package org.example.entity.organism.animal.herbivore;

import org.example.settings.BaseOrganismSettings;

public class Goat extends Herbivore{
    public Goat() {
        super(new BaseOrganismSettings(Goat.class.getSimpleName()));
    }
}
