/**
 * Copyright Vast 2017. All Rights Reserved.
 * <p/>
 * http://www.vast.com
 */
package com.vocumsineratio.kata.probability;

/**
 * @author Danil Suits (danil@vast.com)
 */
class DataModel {

    static Coin createFairCoin() {
        Probability singleTossLandsHeads = new Probability(0.5d);
        return new Coin(singleTossLandsHeads);
    }

    static double toDouble(Probability bothCoinsLandHeads) {
        return bothCoinsLandHeads.toDouble();
    }

    static class Coin implements API.Coin<Probability> {
        final Probability singleTossLandsHeads;

        Coin(Probability singleTossLandsHeads) {
            this.singleTossLandsHeads = singleTossLandsHeads;
        }

        public Probability heads() {
            return singleTossLandsHeads;
        }
    }

    static class Probability implements API.Probability<Probability> {
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
            this((float) value);
        }

        Probability(float value) {
            this.value = value;
        }

        double toDouble() {
            return (double) this.value;
        }

        public Probability combinedWith(Probability that) {
            return new Probability(this.value * that.value);
        }
    }
}
