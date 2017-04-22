/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.selection;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Herros Team
 */
public class ProbabilisticTournament extends Selection {

    public ProbabilisticTournament() {
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population) {
        List<Chromosome> newPopulation = new LinkedList();

        double probability = ThreadLocalRandom.current().nextDouble();
        for (int i = 0; i < population.size(); i++) {
            int positionOfSelected;
            double bestAdaptation;
            int randomPosition = ThreadLocalRandom.current().nextInt(0, population.size());
            positionOfSelected = randomPosition;
            bestAdaptation = population.get(randomPosition).getAdaptation();

            randomPosition = ThreadLocalRandom.current().nextInt(0, population.size());

            double randomProbability = ThreadLocalRandom.current().nextDouble();
            if (randomProbability > probability) {
                if (bestAdaptation < population.get(randomPosition).getAdaptation()) {
                    positionOfSelected = randomPosition;
                }
            } else if (bestAdaptation > population.get(randomPosition).getAdaptation()) {
                positionOfSelected = randomPosition;
            }

            newPopulation.add(population.get(positionOfSelected).copy());
        }
        return newPopulation;
    }

}
