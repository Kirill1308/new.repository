package org.example.ability;

import org.example.entity.organism.animal.Animal;

public interface Reproducible {
    Animal reproduce(Animal partner);
}
