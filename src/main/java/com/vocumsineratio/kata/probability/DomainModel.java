/**
 * Copyright Vast 2017. All Rights Reserved.
 * <p/>
 * http://www.vast.com
 */
package com.vocumsineratio.kata.probability;

/**
 * @author Danil Suits (danil@vast.com)
 */
class DomainModel {
    static <Probability extends API.Probability<Probability>
            , Coin extends API.Coin<Probability>
            >
    Probability bothTossesLandHeads(Coin coin) {
        return bothTossesLandHeads(coin, coin);
    }

    static <Probability extends API.Probability<Probability>
            , Coin extends API.Coin<Probability>
            >
    Probability bothTossesLandHeads(Coin firstCoin, Coin secondCoin) {
        // Boy, this sure looks like a violation of the law of Demeter.  I'm
        // not at all worried about it, because once again we're interacting
        // only with the API, and these are just queries that leave the
        // underlying state unchanged.
        Probability firstTossLandsHeads = firstCoin.heads();
        Probability secondTossLandsHeads = secondCoin.heads();

        return bothEventsHappen(firstTossLandsHeads, secondTossLandsHeads);

    }

    static <Probability extends API.Probability<Probability>> Probability bothEventsHappen(Probability firstEvent, Probability secondEvent) {
        return firstEvent.combinedWith(secondEvent);
    }
}
