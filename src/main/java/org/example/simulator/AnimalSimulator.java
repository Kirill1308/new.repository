package org.example.simulator;

import org.example.entity.map.Island;
import org.example.entity.map.IslandCell;
import org.example.entity.organism.animal.predator.Predator;
import org.example.entity.organism.plant.Plant;
import org.example.entity.organism.animal.Animal;
import org.example.entity.organism.animal.herbivore.Herbivore;
import org.example.printer.StatisticViewProvider;

import java.util.*;
import java.util.stream.Collectors;

public class AnimalSimulator {
    private final StatisticViewProvider provider = new StatisticViewProvider();
    private final List<Object> eatenPrey = new ArrayList<>();
    private final List<Object> eatenPlant = new ArrayList<>();
    private IslandCell currentCell;

    public synchronized void startSimulation(IslandCell islandCell, Island island) {
        currentCell = islandCell;
        List<Animal> animals = islandCell.getAnimals();
        List<Plant> plants = islandCell.getPlants();

        if (animals.isEmpty()) {
            printNoAnimalsPresent();
            provider.printCellCoordinatesAndOrganismCounts(animals, plants, currentCell);
            System.out.println();
        } else {
            provider.printAnimalAndPlantDetails(animals, plants, currentCell);
            runAnimalSimulation(animals, plants, island);
        }
        currentCell = null;
    }

    public void runAnimalSimulation(List<Animal> animals, List<Plant> plants, Island island) {
        while (shouldContinueSimulation(animals, plants)) {
            List<String> events = processAnimalActions(animals, plants, island);
            printSimulationState(events, animals, plants);

            if (shouldEndSimulation(animals, plants)) {
                printSimulationEndedMessage(currentCell);
                break;
            }
        }
    }

    private boolean shouldContinueSimulation(List<Animal> animals, List<Plant> plants) {
        return (hasHerbivores(animals) || hasPredators(animals))
                && hasPlants(plants) || !hasPlants(plants);
    }

    private void printSimulationState(List<String> events, List<Animal> animals, List<Plant> plants) {
        printEvents(events);
        provider.printCellCoordinatesAndOrganismCounts(animals, plants, currentCell);
        System.out.println();
    }


    public List<String> processAnimalActions(List<Animal> animals, List<Plant> plants, Island island) {
        List<String> events = new ArrayList<>();
        List<Animal> toRemove = new ArrayList<>();

        for (Animal animal : new ArrayList<>(animals)) {
            if (!animal.hasEaten()) {
                performRandomAction(animal, animals, plants, events, island);

                if (animal.hasEaten()) {
                    toRemove.add(animal);
                }
            }
        }

        animals.removeAll(toRemove);
        removeEatenEntities(animals, plants);
        return events;
    }


    public void performRandomAction(Animal animal, List<Animal> animals, List<Plant> plants, List<String> events, Island island) {
        Random random = new Random();
        int choice = random.nextInt(3) + 1;
        switch (choice) {
            case 1 -> feedAnimal(animal, animals, plants, events);
            case 2 -> reproduceAnimal(animal, animals, events);
            case 3 -> moveAnimal(animal, animals, events, island);
        }
    }

    public void feedAnimal(Animal animal, List<Animal> animals, List<Plant> plants, List<String> events) {
        Object food = null;

        if (animal.getCurrentSatiety() != 0) {
            if (animal instanceof Predator) {
                Optional<Animal> prey = findPrey(animals);
                if (prey.isPresent()) {
                    food = prey.get();
                    animal.setCurrentSatiety(animal.getCurrentSatiety() - prey.get().getDefaultWeight());
                    eatenPrey.add(food);
                }
            } else if (animal instanceof Herbivore) {
                Optional<Plant> plant = findPlant(plants);
                if (plant.isPresent()) {
                    food = plant.get();
                    animal.setCurrentSatiety(animal.getCurrentSatiety() - plant.get().getDefaultWeight());
                    eatenPlant.add(food);
                }
            }
        } else {
            events.add(String.format("%s (id=%d) is not hungry", animal.getIcon(), animal.getId()));
        }

        if (food != null) {
            animal.eat(food);
            events.add(String.format("%s (id=%d) ate a %s (id=%d)", animal.getIcon(), animal.getId(),
                    (food instanceof Animal) ? ((Animal) food).getIcon() : ((Plant) food).getIcon(),
                    (food instanceof Animal) ? ((Animal) food).getId() : ((Plant) food).getId()));
        } else {
            events.add(String.format("%s (id=%d) did not find any %s.", animal.getIcon(), animal.getId(),
                    (animal instanceof Predator) ? "prey" : "plants"));
        }
    }

    public void reproduceAnimal(Animal animal, List<Animal> animals, List<String> events) {
        if (!animal.hasReproduced()) {
            List<Animal> potentialPartners = findReproductivePartners(animal, animals);

            if (!potentialPartners.isEmpty()) {
                Animal partner = potentialPartners.get(0);

                if (!partner.hasReproduced() && !eatenPrey.contains(partner)) {
                    Animal offspring = animal.reproduce(partner);
                    if (offspring != null) {
                        events.add(String.format("%s (id=%d) and %s (id=%d) reproduced, creating a new %s (id=%d)",
                                animal.getIcon(), animal.getId(), partner.getIcon(), partner.getId(),
                                offspring.getIcon(), offspring.getId()));
                        partner.setReproduced(true);
                        animal.setReproduced(true);
                        animals.add(offspring);
                    }
                } else {
                    events.add(String.format("%s (id=%d) did not find a suitable partner for reproduction.",
                            animal.getIcon(), animal.getId()));
                }
            } else {
                events.add(String.format("%s (id=%d) did not find a suitable partner for reproduction.",
                        animal.getIcon(), animal.getId()));
            }
        }
    }

    private List<Animal> findReproductivePartners(Animal animal, List<Animal> animals) {
        return animals.stream()
                .filter(partner -> partner.getClass() == animal.getClass() && partner != animal)
                .collect(Collectors.toList());
    }

    public void moveAnimal(Animal animal, List<Animal> animals, List<String> events, Island island) {
        boolean moved = false;

        while (!moved) {
            IslandCell targetCell = findTargetCell(island);
            if (canBeMoved(animal, targetCell)) {
                targetCell.addAnimal(animal);
                events.add(String.format("%s (id=%d) moved to cell[%d][%d]", animal.getIcon(), animal.getId(), targetCell.getX(), targetCell.getY()));
                animals.remove(animal);
                moved = true;
            }
        }
    }

    private boolean canBeMoved(Animal animal, IslandCell targetCell) {
        List<Animal> animalsInTarget = targetCell.getAnimals();
        int countAnimalsOfType = provider.countAnimalsOfType(animalsInTarget, animal.getClass());
        return countAnimalsOfType < animal.getMaxPopulation();
    }

    private IslandCell findTargetCell(Island island) {
        int currentX = currentCell.getX();
        int currentY = currentCell.getY();

        Random random = new Random();
        int direction = random.nextInt(4);

        int targetX = currentX;
        int targetY = currentY;

        switch (direction) {
            case 0 -> targetX = Math.max(0, currentX - 1);  // Left
            case 1 -> targetX = Math.min(island.getCells().length - 1, currentX + 1);  // Right
            case 2 -> targetY = Math.max(0, currentY - 1);  // Up
            case 3 -> targetY = Math.min(island.getCells()[0].length - 1, currentY + 1);  // Down
        }

        return island.getTargetCell(targetX, targetY);
    }


    private void removeEatenEntities(List<Animal> animals, List<Plant> plants) {
        animals.removeIf(eatenPrey::contains);
        plants.removeIf(eatenPlant::contains);
    }

    private Optional<Animal> findPrey(List<Animal> animals) {
        return animals.stream()
                .filter(animal -> animal instanceof Herbivore && !animal.hasEaten())
                .findFirst();
    }

    private Optional<Plant> findPlant(List<Plant> plants) {
        return plants.stream()
                .filter(plant -> !plant.hasEaten())
                .findFirst();
    }

    private void printEvents(List<String> events) {
        if (!events.isEmpty()) {
            System.out.println("=".repeat(117));
            events.forEach(System.out::println);
            System.out.println();
        }
    }

    private void printNoAnimalsPresent() {
        System.out.println("=".repeat(117));
        System.out.println("No animals are present in cell [" + currentCell.getX() + "][" + currentCell.getY() + "].");
    }

    private synchronized void printSimulationEndedMessage(IslandCell islandCell) {
        System.out.println("=".repeat(117));
        System.out.println("Simulation ended in cell [" + islandCell.getX() + "][" + islandCell.getY() + "].");
    }

    private boolean shouldEndSimulation(List<Animal> animals, List<Plant> plants) {
        if (animals.isEmpty() || (countPredators(animals) == 0)) return true;
        if (countHerbivores(animals) > 0 && !hasPlants(plants)) return true;

        for (Animal animal : animals) {
            if (!animal.hasPair()) return true;
        }
        return false;
    }

    private boolean hasHerbivores(List<Animal> animals) {
        return animals.stream().anyMatch(animal -> animal instanceof Herbivore && !animal.hasEaten());
    }

    private boolean hasPredators(List<Animal> animals) {
        return animals.stream().anyMatch(animal -> animal instanceof Predator);
    }

    private boolean hasPlants(List<Plant> plants) {
        return plants.stream().anyMatch(plant -> !plant.hasEaten());
    }

    private int countPredators(List<Animal> animals) {
        return (int) animals.stream().filter(animal -> animal instanceof Predator).count();
    }

    private int countHerbivores(List<Animal> animals) {
        return (int) animals.stream().filter(animal -> animal instanceof Herbivore).count();
    }
}
