package org.example.entity.organism.animal.herbivore;

import org.example.settings.BaseOrganismSettings;

public class Boar extends Herbivore{
    public Boar() {
        super(new BaseOrganismSettings(Boar.class.getSimpleName()));
    }
}
