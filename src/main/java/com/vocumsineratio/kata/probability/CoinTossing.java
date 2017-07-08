/**
 * Copyright Vast 2017. All Rights Reserved.
 * <p/>
 * http://www.vast.com
 */
package com.vocumsineratio.kata.probability;

/**
 * @author Danil Suits (danil@vast.com)
 */
public class CoinTossing {
    static class Probability {
        final double value;

        Probability(double value) {
            this.value = value;
        }
    }

    public static double bothCoinsLandHeads() {
        // This is a property specific to coins
        Probability singleTossLandsHeads = new Probability(0.5d);

        // We're making a statement about something happening twice, therefore
        // two events.  It happens that the two events share the same distribution
        // of outcomes, so they are anchored to the same universal probability.
        Probability firstTossLandsHeads = singleTossLandsHeads;
        Probability secondTossLandsHeads = singleTossLandsHeads;

        Probability bothCoinsLandHeads = combine(firstTossLandsHeads, secondTossLandsHeads);

        return bothCoinsLandHeads.value;
    }

    private static Probability combine(Probability firstTossLandsHeads, Probability secondTossLandsHeads) {
        final double probabilityBothCoinsLandHeads = firstTossLandsHeads.value * secondTossLandsHeads.value;

        return new Probability(probabilityBothCoinsLandHeads);
    }
}
