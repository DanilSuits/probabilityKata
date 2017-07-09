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

    interface Probability<P extends Probability<P>> {
        P combinedWith(P that);
    }

    interface Coin<P extends Probability<P>> {
        P heads();
    }
}
