package org.example.entity.organism.animal.herbivore;

import org.example.settings.BaseOrganismSettings;

public class Duck extends Herbivore{
    public Duck() {
        super(new BaseOrganismSettings(Duck.class.getSimpleName()));
    }
}
