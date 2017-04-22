/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.crossover;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author usuario_local
 */
public abstract class Crossover {
    
    public abstract List<Chromosome> crossover(Chromosome parent1, Chromosome parent2, int crossPoint);
    
}
