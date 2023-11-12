package org.example.entity.organism.animal.herbivore;

import org.example.settings.BaseOrganismSettings;

public class Deer extends Herbivore{
    public Deer() {
        super(new BaseOrganismSettings(Deer.class.getSimpleName()));
    }
}
