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
        DataModel.Coin fairCoin = DataModel.createFairCoin();

        DataModel.Probability bothTossesLandHeads = DomainModel.bothTossesLandHeads(fairCoin);

        return DataModel.toDouble(bothTossesLandHeads);
    }

}
