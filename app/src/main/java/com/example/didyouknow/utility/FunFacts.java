package com.example.didyouknow.utility;

public class FunFacts {
    private static String[] randomFacts = new String[]{
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    public static String getRandomFact(){
        return randomFacts[(int)Math.random()*randomFacts.length];
    }
}
