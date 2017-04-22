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
public class StochasticUniversal extends Selection {

    public StochasticUniversal() {
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population) {
        List<Chromosome> newPopulation = new LinkedList();
        double space = 1 / population.size();
        double probability = ThreadLocalRandom.current().nextDouble() * space;
        int survivorPos;
        for (Chromosome c : population) {
            survivorPos = 0;
            while (probability > population.get(survivorPos).getScoreAccumulated()
                    && (survivorPos < population.size()-1)) {
                survivorPos++;
            }
            newPopulation.add(population.get(survivorPos));
            probability += space;
        }

        return newPopulation;

    }

}
