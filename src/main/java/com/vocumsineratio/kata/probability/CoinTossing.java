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
        // This represents a specific _decision_ about how we happen to represent probabilities
        // in memory.  By encapsulating it within its own module (the Probability class), we
        // leave ourselves free to change that decision without impacting the rest of the
        // program.
        //
        // See https://www.cs.umd.edu/class/spring2003/cmsc838p/Design/criteria.pdf
        //
        // So if we have done this right, we should be able to isolate the change introduced
        // in part 2 of the kata in this one module.
        final float value;

        Probability(double value) {
            this.value = (float) value;
        }

        Probability(float value) {
            this.value = value;
        }

        double toDouble() {
            return (double)this.value;
        }

        Probability combinedWith(Probability that) {
            return new Probability(this.value * that.value);
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

        Probability bothCoinsLandHeads = firstTossLandsHeads.combinedWith(secondTossLandsHeads);

        return toDouble(bothCoinsLandHeads);
    }

    private static double toDouble(Probability bothCoinsLandHeads) {
        return bothCoinsLandHeads.toDouble();
    }

}
