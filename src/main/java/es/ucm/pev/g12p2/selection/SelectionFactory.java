/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.pev.g12p2.selection;

/**
 *
 * @author Herros Team
 */
public class SelectionFactory {

    public static Selection getSelectionAlgorithm(String selectionAlgorithm) {
        switch (selectionAlgorithm) {
            case "Ruleta":
                return new Roulette();
            case "Estocástico Universal":
                return new StochasticUniversal();
            case "Truncamiento":
                return new Truncation();
            case "Torneo Determinista":
                return new DeterministicTournament();
            case "Torneo Probabilístico":
                return new ProbabilisticTournament();
            default:
                return new Roulette();
        }
    }
}
