/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.crossover;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import es.ucm.pev.g12p2.chromosome.Function;
import es.ucm.pev.g12p2.chromosome.gene.Gene;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Herros Team
 */
public class ERX extends Crossover {

    public ERX() {
    }

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2, int crossPoint) {
        List<Chromosome> children = new LinkedList();

        Function c = (Function) parent1;
        
        Chromosome child1 = new Function(0.05, parent1.getLength(), false, c.getData());
        Chromosome child2 = new Function(0.05, parent1.getLength(), false, c.getData());
        
        int distance[][] = c.getDistanceData();
        boolean majorConflict = true;
        
        int j=0;
        do{
            majorConflict = false;
            child1 = new Function(0.05, parent1.getLength(), false, c.getData());
            child1.getGene(0).addAllele(0, (int)parent2.getGene(j).getAllele(0));
            int chosenCity=(int)parent2.getGene(j).getAllele(0);
            for(int i=1; i<child1.getLength() && !majorConflict ;i++){
                boolean conflict=false;
                do{
                    int aux = chosenCity;
                    List<Integer> leastConnectedCities = this.getLeastConnectedCities(chosenCity, parent1.getLength(), distance);
                    chosenCity = this.getChosenCity(leastConnectedCities);
                    //determine if there is conflict
                    conflict = false;
                    for(Gene g: child1.getGenes()){
                        if(g.getSize() != 0){
                            if(g.getAllele(0).equals(chosenCity)){
                                conflict = true;
                                break;
                            }
                        }
                    }
                    if(conflict){
                        chosenCity = aux;
                    }
                    if(conflict && leastConnectedCities.size() == 1){
                       majorConflict = true;
                    }
                }while(conflict && !majorConflict);
                child1.getGene(i).addAllele(0, chosenCity);
            }
            j++;
        }while(majorConflict);
        
        majorConflict = true;
        j=0;
        do{
            majorConflict = false;
            child2 = new Function(0.05, parent1.getLength(), false, c.getData());
            child2.getGene(0).addAllele(0, (int)parent1.getGene(j).getAllele(0));
            
            int chosenCity=(int)parent1.getGene(j).getAllele(0);
            for(int i=1; i<child2.getLength() && !majorConflict;i++){
                boolean conflict=false;
                do{
                    int aux = chosenCity;
                    List<Integer> leastConnectedCities = this.getLeastConnectedCities(chosenCity, parent1.getLength(), distance);
                    chosenCity = this.getChosenCity(leastConnectedCities);
                    //determine if there is conflict
                    conflict = false;
                    for(Gene g: child2.getGenes()){
                        if(g.getSize() != 0){
                            if(g.getAllele(0).equals(chosenCity)){
                                conflict = true;
                                break;
                            }
                        }
                    }
                    if(conflict){
                        chosenCity = aux;
                    }
                    if(conflict && leastConnectedCities.size() == 1){
                       majorConflict = true;
                    }
                }while(conflict && !majorConflict);
                child2.getGene(i).addAllele(0, chosenCity);
            }
        j++;
        }while(majorConflict);
        
        
        child1.evaluate();
        child2.evaluate();
        children.add(child1);
        children.add(child2);

        return children;
    }
    
    private List<Integer> getLeastConnectedCities(int initial, int length, int[][] distance){
        HashMap<Integer,Integer> connectedCities = new HashMap<>();
        
        for(int i=0; i<length;i++){
            if(distance[initial-1][i] != 0){
                connectedCities.put(i+1, 0);
            }
        }
        
        for(Map.Entry<Integer, Integer> entry : connectedCities.entrySet()) {
            Integer key = entry.getKey();
            int numConnections=0;
            for(int i=0; i<length;i++){
                if(distance[key-1][i] != 0){
                    numConnections++;
                }
            }
            connectedCities.put(key, numConnections);
        }

        int minimumValue = Integer.MAX_VALUE;
        
        for(Map.Entry<Integer, Integer> entry : connectedCities.entrySet()) {
            Integer value = entry.getValue();
            if(value < minimumValue) {
                minimumValue = value;
            }
        }
        
        List<Integer> leastConnectedCities = new LinkedList<>();
        
        for(Map.Entry<Integer, Integer> entry : connectedCities.entrySet()) {
            if(entry.getValue() == minimumValue) {
              leastConnectedCities.add(entry.getKey());
            }
        }
        
        return leastConnectedCities;
    }
    
    private Integer getChosenCity(List<Integer> cities){
        if(cities.size() == 1){
            return cities.get(0);
        }
        else{
            Random randomizer = new Random();
            return cities.get(randomizer.nextInt(cities.size()));
        }
    }

}

 