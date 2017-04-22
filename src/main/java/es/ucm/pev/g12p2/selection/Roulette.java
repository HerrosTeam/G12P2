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

/**
 *
 * @author Herros Team
 */
public class Roulette extends Selection {

    public Roulette() {
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population) {
        List<Chromosome> newPopulation = new LinkedList();
        List<Integer> sel_surv= new LinkedList();
        double probability;
        int survivorPos;
        Random randomNumber = new Random();
        for (int i=0; i<population.size();i++) {
            probability = randomNumber.nextDouble();
            survivorPos = 0;
            while (probability > population.get(survivorPos).getScoreAccumulated()
                    && (survivorPos < (population.size() -1 )) ){
                survivorPos++;
            }
            sel_surv.add(i, survivorPos);
        }
        for (int i=0; i<population.size();i++) {
             newPopulation.add(i, population.get(sel_surv.get(i)));
        }
       
        return newPopulation;
    }

}
