package org.example.entity.organism.plant;

import org.example.settings.BaseOrganismSettings;

public class Grass extends Plant {
    public Grass(){
        super(new BaseOrganismSettings(Grass.class.getSimpleName()));
    }
}
