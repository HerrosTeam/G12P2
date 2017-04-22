/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.chromosome.gene;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author usuario_local
 */
public class IntegerGene extends Gene{
    private int min;
    private int max;
    
    public IntegerGene(int geneLength, int min, int max) {
        super(geneLength);
        this.min=min;
        this.max=max;
    }

    @Override
    public void initializeGene(int location) {
        this.allele.add(location);
    }
    
    @Override
    public void mutate(int i) {
        this.allele.set(0, ThreadLocalRandom.current().nextInt()*(max - min) + min);
    }
    
    public int getIntegerAllele(){
        return (int) this.allele.get(0);
    }
    
    public void setAllele(int newValue){
        this.allele.set(0, newValue);
    }

    @Override
    public Gene copy() {
        IntegerGene gene = new IntegerGene(this.getLength(), min, max);
        gene.getAlleleList().clear();
        gene.getAlleleList().add(0, this.allele.get(0));
        return gene;
    }

}
