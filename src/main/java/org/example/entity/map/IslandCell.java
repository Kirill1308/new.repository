package org.example.entity.map;

import org.example.entity.organism.plant.Plant;
import org.example.entity.organism.animal.Animal;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IslandCell {
    private final int x;
    private final int y;
    private final List<Animal> animals;
    private final List<Plant> plants;

    public IslandCell(int x, int y) {
        this.x = x;
        this.y = y;
        animals = new ArrayList<>();
        plants = new ArrayList<>();
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

}
