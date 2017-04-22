/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.mutation;

import es.ucm.pev.g12p2.crossover.CX;
import es.ucm.pev.g12p2.crossover.Crossover;
import es.ucm.pev.g12p2.crossover.ERX;
import es.ucm.pev.g12p2.crossover.OX;
import es.ucm.pev.g12p2.crossover.OXPriorityOrder;
import es.ucm.pev.g12p2.crossover.OrdinalCodification;
import es.ucm.pev.g12p2.crossover.PMX;
import es.ucm.pev.g12p2.crossover.SinglePoint;
import es.ucm.pev.g12p2.crossover.TwoPoint;

/**
 *
 * @author Herros Team
 */
public class MutationFactory {
    
    public static Mutation getMutationAlgorithm(String mutationAlgorithm, double probabilityOfMutation, int populationSize,
            int numSelIns) {
        
        switch (mutationAlgorithm) {
            case "Básica":
                return new BasicMutation(probabilityOfMutation,populationSize);
            case "Inserción":
                return new InsertionMutation(probabilityOfMutation,populationSize, numSelIns);
            case "Intercambio":
                return new SwapMutation(probabilityOfMutation,populationSize);
            case "Inversión":
                return new InversionMutation(probabilityOfMutation,populationSize);
            case "Heurística":
                return new HeuristicMutation(probabilityOfMutation,populationSize, numSelIns, false);
            default:
                return new BasicMutation(probabilityOfMutation,populationSize);
        }
    }
}
