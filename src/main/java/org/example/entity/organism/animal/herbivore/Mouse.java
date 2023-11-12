package org.example.entity.organism.animal.herbivore;

import org.example.settings.BaseOrganismSettings;

public class Mouse extends Herbivore{
    public Mouse() {
        super(new BaseOrganismSettings(Mouse.class.getSimpleName()));
    }
}
