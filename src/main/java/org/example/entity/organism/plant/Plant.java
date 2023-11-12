package org.example.entity.organism.plant;

import org.example.entity.organism.Organism;
import org.example.settings.BaseOrganismSettings;
import lombok.Getter;

@Getter
public abstract class Plant extends Organism {

    private boolean eaten = false;


    public Plant(BaseOrganismSettings organismSettings) {
        super(organismSettings);
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    public boolean hasEaten() {
        return eaten;
    }

}
