/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.crossover;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Herros Team
 */
public class OXModified extends Crossover {

    public OXModified() {
    }

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2, int crossPoint) {
        List<Chromosome> children = new LinkedList();
        //copiamos los padres
        Chromosome child1 = parent2.copy();
        Chromosome child2 = parent1.copy();
        //obtenemos el numero de puntos que seleccionaremos
        int numSelected = ThreadLocalRandom.current().nextInt(1, parent1.getLength());
        //guardamos los numSelected alelos y la posicion en la que se encuentra del padre1
        Map<Integer, Integer> selectedValuesParent1 = new LinkedHashMap();
        
        int randomPos;
        for(int i=0; i< numSelected; i++){
            randomPos = ThreadLocalRandom.current().nextInt(0, parent1.getLength());
            while(selectedValuesParent1.containsValue(randomPos)){
                randomPos = ThreadLocalRandom.current().nextInt(0, parent1.getLength());
            }
            selectedValuesParent1.put((int)parent1.getGene(randomPos).getAllele(0), randomPos);
        }
        
        //guardamos los numSelected alelos y la posicion en la que se encuentra del hijo2
        Map<Integer, Integer> selectedValuesParent2 = new LinkedHashMap();
        for(int i=0; selectedValuesParent2.size() < numSelected && 
                i < child2.getLength(); i++){
            if(selectedValuesParent1.containsKey(parent2.getGene(i).getAllele(0))){
                selectedValuesParent2.put((int)parent2.getGene(i).getAllele(0), i);
            }
        }
        
        //insertamos los alelos restantes del hijo2 en el 1 respetando el orden
        List<Integer> kp1 = new ArrayList<>(selectedValuesParent1.keySet());
        List<Integer> vp2 = new ArrayList<>(selectedValuesParent2.values());
        List<Integer> kp2 = new ArrayList<>(selectedValuesParent2.keySet());
        List<Integer> vp1 = new ArrayList<>(selectedValuesParent1.values());
        
        
        for(int i=0; i<selectedValuesParent1.size();i++){
            child1.getGene(vp2.get(i)).setAllele(0,kp1.get(i));
            child2.getGene(vp1.get(i)).setAllele(0,kp2.get(i));
        }

       
        child1.evaluate();
        child2.evaluate();
        children.add(child1);
        children.add(child2);

        return children;
    }
    
}

 
