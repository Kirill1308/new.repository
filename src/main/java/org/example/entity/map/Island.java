package org.example.entity.map;

import org.example.entity.organism.plant.Grass;
import org.example.entity.organism.plant.Plant;
import org.example.entity.organism.animal.Animal;
import org.example.entity.organism.animal.AnimalType;
import org.example.reader.FilePropertyReader;
import lombok.Getter;

import java.util.List;
import java.util.Random;

public class Island {
    private static final int MAX_PLANTS = 10;
    private static final int MAX_ANIMALS = 10;
    @Getter
    private final IslandCell[][] cells;
    private final FilePropertyReader filePropertyReader = new FilePropertyReader();
    private final Random random = new Random();

    public Island(int width, int height) {
        cells = new IslandCell[width][height];
        initializeIslandCells();
        loadAnimalProperties();
    }

    private void initializeIslandCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new IslandCell(i, j);
            }
        }
    }

    private void loadAnimalProperties() {
        filePropertyReader.loadAnimalProperties();
    }

    public void populateIsland() {
        for (IslandCell[] islandCells : cells) {
            for (IslandCell cell : islandCells) {
                addRandomNumOfPlantsToCell(cell);
                addRandomAnimalsToCell(cell);
            }
        }
    }

    private void addRandomAnimalsToCell(IslandCell cell) {
        int numAnimals = random.nextInt(MAX_ANIMALS);
        for (int k = 0; k < numAnimals; k++) {
            addRandomAnimalToCell(cell);
        }
    }

    private void addRandomAnimalToCell(IslandCell cell) {
        AnimalType[] animalTypes = AnimalType.values();
        int randomIndex = random.nextInt(animalTypes.length);
        AnimalType animalType = animalTypes[randomIndex];
        if (canAddAnimal(animalType.getAnimalClass(), cell)) {
            addAnimalToCell(animalType.getAnimalClass(), cell);
        }
    }

    private boolean canAddAnimal(Class<? extends Animal> animalClass, IslandCell cell) {
        int maxQuantity = Integer.parseInt(filePropertyReader.loadMaxQuantity(animalClass.getSimpleName()));
        return countAnimals(animalClass, cell) < maxQuantity;
    }

    private void addAnimalToCell(Class<? extends Animal> animalClass, IslandCell cell) {
        try {
            Animal animal = animalClass.getDeclaredConstructor().newInstance();
            cell.addAnimal(animal);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating an animal instance", e);
        }
    }

    private void addRandomNumOfPlantsToCell(IslandCell cell) {
        int numOfPlants = random.nextInt(MAX_PLANTS);
        List<Plant> plants = cell.getPlants();
        for (int i = 0; i < numOfPlants; i++) {
            plants.add(new Grass());
        }
    }

    private int countAnimals(Class<? extends Animal> animalClass, IslandCell cell) {
        int count = 0;
        List<Animal> animals = cell.getAnimals();
        for (Animal animal : animals) {
            if (animalClass.isInstance(animal)) {
                count++;
            }
        }

        return count;
    }

    public IslandCell getTargetCell(int targetX, int targetY) {
        return cells[targetX][targetY];
    }
}
