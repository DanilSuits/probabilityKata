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
    public static double bothCoinsLandHeads() {
        final double probabilitySingleCointLandsHeads = .5d;
        final double probabilityBothCoinsLandHeads = probabilitySingleCointLandsHeads * probabilitySingleCointLandsHeads;
        return probabilityBothCoinsLandHeads;
    }
}
