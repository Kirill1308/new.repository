package org.example.entity.organism;

import lombok.Getter;
import org.example.settings.BaseOrganismSettings;

@Getter
public abstract class Organism {
    private final String icon;
    private final double defaultWeight;
    private final int maxPopulation;
    private final int id;
    private static int lastId = 0;

    public Organism(BaseOrganismSettings organismSettings) {
        this.icon = organismSettings.getIcon();
        this.maxPopulation = organismSettings.getMaxQuantity();
        this.defaultWeight = organismSettings.getWeight();
        this.id = lastId++;
    }
}
