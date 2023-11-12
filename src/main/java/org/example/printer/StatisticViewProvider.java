package org.example.printer;

import org.example.entity.map.IslandCell;
import org.example.entity.organism.plant.Grass;
import org.example.entity.organism.plant.Plant;
import org.example.entity.organism.Organism;
import org.example.entity.organism.animal.Animal;
import org.example.entity.organism.animal.herbivore.*;
import org.example.entity.organism.animal.predator.*;
import org.example.reader.FilePropertyReader;

import java.util.List;

public class StatisticViewProvider {
    private final FilePropertyReader propertyReader = new FilePropertyReader();

    public synchronized void printCellCoordinatesAndOrganismCounts(List<Animal> animals, List<Plant> plants, IslandCell islandCell) {
        System.out.printf("(%d, %2d)\t", islandCell.getX(), islandCell.getY());
        printAllOrganismCounts(animals, plants);
    }


    private void printAllOrganismCounts(List<Animal> animals, List<Plant> plants) {
        printOrganismCountAndEmoji(animals, Wolf.class);
        printOrganismCountAndEmoji(animals, Bear.class);
        printOrganismCountAndEmoji(animals, Python.class);
        printOrganismCountAndEmoji(animals, Eagle.class);
        printOrganismCountAndEmoji(animals, Fox.class);
        printOrganismCountAndEmoji(animals, Boar.class);
        printOrganismCountAndEmoji(animals, Buffalo.class);
        printOrganismCountAndEmoji(animals, Deer.class);
        printOrganismCountAndEmoji(animals, Duck.class);
        printOrganismCountAndEmoji(animals, Goat.class);
        printOrganismCountAndEmoji(animals, Mouse.class);
        printOrganismCountAndEmoji(animals, Sheep.class);
        printOrganismCountAndEmoji(animals, Horse.class);
        printOrganismCountAndEmoji(plants, Grass.class);
    }

    private void printOrganismCountAndEmoji(List<? extends Organism> organisms, Class<? extends Organism> organismType) {
        long count = countEntitiesOfType(organisms, organismType);
        String emoji = propertyReader.loadIcon(organismType.getSimpleName());
        System.out.printf("%s(%d)\t", emoji, count);
    }

    private long countEntitiesOfType(List<? extends Organism> entities, Class<? extends Organism> entityType) {
        return entities.stream().filter(entityType::isInstance).count();
    }

    public void printAnimalAndPlantDetails(List<Animal> animals, List<Plant> plants, IslandCell islandCell) {
        System.out.println("=".repeat(117));
        System.out.println("Current organisms in cell [" + islandCell.getX() + "][" + islandCell.getY() + "]:");

        printOrganismsDetails(plants);
        printOrganismsDetails(animals);
    }

    private void printOrganismsDetails(List<? extends Organism> organisms) {
        for (Organism organism : organisms) {
            String details = organism.getIcon() + " id = " + organism.getId();
            System.out.println(details);
        }
    }

    public void printCellStatistics(IslandCell[][] cells) {
        for (IslandCell[] cell : cells) {
            for (IslandCell islandCell : cell) {
                List<Animal> animals = islandCell.getAnimals();
                List<Plant> plants = islandCell.getPlants();
                printCellCoordinatesAndOrganismCounts(animals, plants, islandCell);
                System.out.println();
            }
        }
    }

    public int countAnimalsOfType(List<Animal> animals, Class<? extends Animal> animalType) {
        return countOrganismsOfType(animals, animalType);
    }

    private int countOrganismsOfType(List<? extends Organism> entities, Class<? extends Organism> entityType) {
        return (int) entities.stream().filter(entityType::isInstance).count();
    }
}
