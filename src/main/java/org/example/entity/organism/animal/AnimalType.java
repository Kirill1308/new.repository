package org.example.entity.organism.animal;

import org.example.entity.organism.animal.herbivore.*;
import org.example.entity.organism.animal.predator.*;
import lombok.Getter;

import org.example.settings.BaseOrganismSettings;

@Getter
public enum AnimalType {
    WOLF(Wolf.class, new BaseOrganismSettings(Wolf.class.getSimpleName())),
    BEAR(Bear.class, new BaseOrganismSettings(Bear.class.getSimpleName())),
    PYTHON(Python.class, new BaseOrganismSettings(Python.class.getSimpleName())),
    EAGLE(Eagle.class, new BaseOrganismSettings(Eagle.class.getSimpleName())),
    FOX(Fox.class, new BaseOrganismSettings(Fox.class.getSimpleName())),
    RABBIT(Rabbit.class, new BaseOrganismSettings(Rabbit.class.getSimpleName())),
    BOAR(Boar.class, new BaseOrganismSettings(Boar.class.getSimpleName())),
    BUFFALO(Buffalo.class, new BaseOrganismSettings(Buffalo.class.getSimpleName())),
    DEER(Deer.class, new BaseOrganismSettings(Deer.class.getSimpleName())),
    DUCK(Duck.class, new BaseOrganismSettings(Duck.class.getSimpleName())),
    GOAT(Goat.class, new BaseOrganismSettings(Goat.class.getSimpleName())),
    MOUSE(Mouse.class, new BaseOrganismSettings(Mouse.class.getSimpleName())),
    SHEEP(Sheep.class, new BaseOrganismSettings(Sheep.class.getSimpleName())),
    HORSE(Horse.class, new BaseOrganismSettings(Horse.class.getSimpleName())),
    CATERPILLAR(Caterpillar.class, new BaseOrganismSettings(Caterpillar.class.getSimpleName()));

    private final Class<? extends Animal> animalClass;
    private final BaseOrganismSettings animalSettings;

    AnimalType(Class<? extends Animal> animalClass, BaseOrganismSettings animalSettings) {
        this.animalClass = animalClass;
        this.animalSettings = animalSettings;
    }
}
