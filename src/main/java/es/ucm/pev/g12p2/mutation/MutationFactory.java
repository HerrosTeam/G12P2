/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.mutation;

/**
 *
 * @author Herros Team
 */
public class MutationFactory {
    
    public static Mutation getMutationAlgorithm(String mutationAlgorithm, double probabilityOfMutation, int populationSize,
            int numSelIns) {
        
        switch (mutationAlgorithm) {
            case "Inserción":
                return new InsertionMutation(probabilityOfMutation,populationSize, numSelIns);
            case "Intercambio":
                return new SwapMutation(probabilityOfMutation,populationSize);
            case "Inversión":
                return new InversionMutation(probabilityOfMutation,populationSize);
            case "Heurística":
                return new HeuristicMutation(probabilityOfMutation,populationSize, numSelIns, false);
            case "Intercambio Multiple":
                return new MultipleSwapMutation(probabilityOfMutation,populationSize);
            default:
                return new SwapMutation(probabilityOfMutation,populationSize);
        }
    }
}
