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
    // TODO: wow, what an awful name.
    interface ProbabilityContract<P extends ProbabilityContract<P>> {
        P combinedWith(P that);
    }

    static class Probability implements ProbabilityContract<Probability> {
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
            return (double)this.value;
        }

        public Probability combinedWith(Probability that) {
            return new Probability(this.value * that.value);
        }
    }

    static class Coin<P extends ProbabilityContract<P>> {
        final P singleTossLandsHeads;

        Coin(P singleTossLandsHeads) {
            this.singleTossLandsHeads = singleTossLandsHeads;
        }
    }


    static class DataModel {

        static Coin<Probability> createFairCoin() {
            Probability singleTossLandsHeads = new Probability(0.5d);
            return new Coin<>(singleTossLandsHeads);
        }

        static double toDouble(Probability bothCoinsLandHeads) {
            return bothCoinsLandHeads.toDouble();
        }
    }

    static class DomainModel {
        static <P extends ProbabilityContract<P>> P bothTossesLandHeads(Coin<P> coin) {
            return bothTossesLandHeads(coin, coin);
        }

        static <P extends ProbabilityContract<P>> P bothTossesLandHeads(Coin<P> firstCoin, Coin<P> secondCoin) {
            // Boy, this sure looks like a violation of the law of Demeter.  I'm
            // not at all worried about it, because once again we're interacting
            // only with the API, and these are just queries that leave the
            // underlying state unchanged.
            P firstTossLandsHeads = firstCoin.singleTossLandsHeads;
            P secondTossLandsHeads = secondCoin.singleTossLandsHeads;

            return bothEventsHappen(firstTossLandsHeads, secondTossLandsHeads);

        }

        static <P extends ProbabilityContract<P>> P bothEventsHappen(P firstEvent, P secondEvent) {
            return firstEvent.combinedWith(secondEvent);
        }
    }

    public static double bothCoinsLandHeads() {
        // This is a property specific to coins
        Coin<Probability> fairCoin = DataModel.createFairCoin();

        Probability bothTossesLandHeads = DomainModel.bothTossesLandHeads(fairCoin);

        return DataModel.toDouble(bothTossesLandHeads);
    }

}
