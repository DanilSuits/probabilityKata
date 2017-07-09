/**
 * Copyright Vast 2017. All Rights Reserved.
 * <p/>
 * http://www.vast.com
 */
package com.vocumsineratio.kata.probability;

/**
 * @author Danil Suits (danil@vast.com)
 */
class API {

    interface Probability<Probability extends API.Probability> {
        Probability combinedWith(Probability that);
    }

    interface Coin<Probability extends API.Probability> {
        Probability heads();
    }
}
