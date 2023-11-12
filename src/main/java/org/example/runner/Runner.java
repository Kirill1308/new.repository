package org.example.runner;

import org.example.entity.map.Island;
import org.example.entity.map.IslandCell;
import org.example.printer.StatisticViewProvider;
import org.example.simulator.AnimalSimulator;
import org.example.usermanagement.UserHandler;

public class Runner {
    private final UserHandler userHandler = new UserHandler();
    private final StatisticViewProvider provider = new StatisticViewProvider();
    private final AnimalSimulator simulator = new AnimalSimulator();

    public void run() {
        userHandler.readIslandDimensions();
        Island island = new Island(userHandler.width, userHandler.height);
        island.populateIsland();

        System.out.println("=".repeat(117));
        System.out.println("Island is populated!");
        System.out.println("=".repeat(117));

        IslandCell[][] cells = island.getCells();
        provider.printCellStatistics(cells);

        runSimulationsInParallel(cells, island);

        System.out.println("=".repeat(117));
        System.out.println("Finish!");
        System.out.println("=".repeat(117));
        provider.printCellStatistics(cells);
        System.out.println("=".repeat(117));
    }

    private void runSimulationsInParallel(IslandCell[][] cells, Island island) {
        Thread[][] threads = new Thread[cells.length][cells[0].length];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                int finalI = i;
                int finalJ = j;
                threads[i][j] = new Thread(() -> simulator.startSimulation(cells[finalI][finalJ], island));
                threads[i][j].start();
            }
        }

        for (Thread[] threadRow : threads) {
            for (Thread thread : threadRow) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Thread interrupted while waiting for completion: ", e);
                }
            }
        }
    }
}
