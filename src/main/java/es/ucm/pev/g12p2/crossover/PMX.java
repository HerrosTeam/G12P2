/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.crossover;

import es.ucm.pev.g12p2.chromosome.Chromosome;
import es.ucm.pev.g12p2.chromosome.Function;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Herros Team
 */
public class PMX extends Crossover {

    public PMX() {
    }

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2, int crossPoint) {
        List<Chromosome> children = new LinkedList();

        Chromosome child1 = parent1.copy();
        Chromosome child2 = parent2.copy();
       /* System.out.println("Padre 1:");
        for(int i=0; i< parent1.getLength(); i++){
            System.out.println(parent1.getGene(i).getAllele(0));
        }
        
        System.out.println("Padre 2:");
        for(int i=0; i< parent1.getLength(); i++){
            System.out.println(parent2.getGene(i).getAllele(0));
        }*/
        int crossPoint2 = ThreadLocalRandom.current().nextInt(0, parent1.getLength());
        
        while (crossPoint == crossPoint2){
            crossPoint2 = ThreadLocalRandom.current().nextInt(0, parent1.getLength());
        }
        
        if(crossPoint > crossPoint2){
            int aux = crossPoint;
            crossPoint = crossPoint2;
            crossPoint2 = aux;
        }
        
        Map<Integer, Integer> selectedValuesChild1 = new HashMap();
        Map<Integer, Integer> selectedValuesChild2 = new HashMap();
        
        for(int i=crossPoint; i<=crossPoint2; i++){
            child1.getGene(i).setAllele(0,parent2.getGene(i).getAllele(0));
            selectedValuesChild1.put((int)child1.getGene(i).getAllele(0), i);
            child2.getGene(i).setAllele(0,parent1.getGene(i).getAllele(0));
            selectedValuesChild2.put((int)child2.getGene(i).getAllele(0), i);
        }
        
        //recorremos cada hijo y si tiene algun alelo repetido se intercambia con el del otro padre
        List<Integer> badPositionsChild1 = new ArrayList();
        List<Integer> badPositionsChild2 = new LinkedList();
        for(int i=0; i<crossPoint; i++){
            if(!selectedValuesChild1.containsKey((int)child1.getGene(i).getAllele(0))){
                selectedValuesChild1.put((int)child1.getGene(i).getAllele(0), i);
            }
            else{
                badPositionsChild1.add(i);
            }
            if(!selectedValuesChild2.containsKey((int)child2.getGene(i).getAllele(0))){
                selectedValuesChild2.put((int)child2.getGene(i).getAllele(0), i);
            }
            else{
                badPositionsChild2.add(i);
            }
        }
     
        
        for(int i=crossPoint2+1; i<parent1.getLength(); i++){
            if(!selectedValuesChild1.containsKey((int)child1.getGene(i).getAllele(0))){
                selectedValuesChild1.put((int)child1.getGene(i).getAllele(0), i);
            }
            else{
                badPositionsChild1.add(i);
            }
            if(!selectedValuesChild2.containsKey((int)child2.getGene(i).getAllele(0))){
                selectedValuesChild2.put((int)child2.getGene(i).getAllele(0), i);
            }
            else{
                badPositionsChild2.add(i);
            }
        }
        
        //intercambiamos pares repetidos
        for(int i=0; i<badPositionsChild1.size(); i++){
            child1.getGene(badPositionsChild1.get(i)).setAllele(0,parent2.getGene(badPositionsChild2.get(i)).getAllele(0));
            child2.getGene(badPositionsChild2.get(i)).setAllele(0,parent1.getGene(badPositionsChild1.get(i)).getAllele(0));
        }
        
        child1.evaluate();
        child2.evaluate();
        
        children.add(child1);
        children.add(child2);

        return children;
    }

    
    
        public static void main(String[] args) {
        //launch(args);
        
        Chromosome c = new Function(0.05, 9, true, "datos12.dat");
        c.inicializeChromosome();
        c.evaluate();
        for (int i=0; i<c.getLength(); i++){
            c.getGene(i).setAllele(0, i+1);
        }
        
        Chromosome c2 = new Function(0.05, 9, true,"datos12.dat");
        c2.inicializeChromosome();
        c2.evaluate();
        c2.getGene(0).setAllele(0, 4);
        c2.getGene(1).setAllele(0, 5);
        c2.getGene(2).setAllele(0, 2);
        c2.getGene(3).setAllele(0, 1);
        c2.getGene(4).setAllele(0, 8);
        c2.getGene(5).setAllele(0, 7);
        c2.getGene(6).setAllele(0, 6);
        c2.getGene(7).setAllele(0, 9);
        c2.getGene(8).setAllele(0, 3);
        List<Chromosome> list = new LinkedList();
        list.add(c2);
        list.add(c);
        for(int i=0; i< list.size(); i++){
            System.out.println("Cromosoma: " + i);
            for(int j=0; j<9; j++){
                System.out.print(list.get(i).getGene(j).getAllele(0)+ ", ");
            }
            System.out.println();
        }
        System.out.println();
    
        int point = ThreadLocalRandom.current().nextInt(0, 9);
        Crossover cross = new OrdinalCodification();
        
        list = cross.crossover(c, c2, point);
        for(int i=0; i< list.size(); i++){
            System.out.println("Cromosoma: " + i);
            for(int j=0; j<9; j++){
                System.out.print(list.get(i).getGene(j).getAllele(0)+ ", ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    
}

 