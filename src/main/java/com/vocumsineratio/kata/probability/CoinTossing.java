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
        Probability singleCoinLandsHeads = new Probability(0.5d);

        final double probabilityBothCoinsLandHeads = singleCoinLandsHeads.value * singleCoinLandsHeads.value;
        return probabilityBothCoinsLandHeads;
    }
}
