package com.example.didyouknow.utility;

public class FunFacts {
    private static String[] randomFacts = new String[]{
            "Berlin ist 9x größer als Paris und hat mehr Brücken als Venedig.",
            "Deutsch ist die am meisten gesprochene Muttersprache in Europa.",
            "Donaudampfschifffahrtselektrizitätenhauptbetriebswerkbauunterbeamtengesellschaft hat 79 Buchstaben.",
            "In Frankreich kann auch post mortem geheiratet werden.",
            "Seit 2016 müssen in Frankreich Essensüberreste von Supermärkten gespendet werden."
    };

    public static String getRandomFact(){
        return randomFacts[(int)Math.random()*randomFacts.length];
    }
}
