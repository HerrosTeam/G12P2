/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.selection;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Herros Team
 */
public class DeterministicTournament extends Selection {

    public DeterministicTournament() {
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population) {
        List<Chromosome> newPopulation = new LinkedList();

        for (int i = 0; i < population.size(); i++) {
            int positionOfBest;
            double bestAdaptation;
            int random = ThreadLocalRandom.current().nextInt(0, population.size());
            positionOfBest = random;
            bestAdaptation = population.get(random).getAdaptation();

            random = ThreadLocalRandom.current().nextInt(0, population.size());
            if (bestAdaptation < population.get(random).getAdaptation()) {
                positionOfBest = random;
            }

            newPopulation.add(i, population.get(positionOfBest).copy());
        }
        return newPopulation;
    }

}
