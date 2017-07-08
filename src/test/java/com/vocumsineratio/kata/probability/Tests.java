/**
 * Copyright Vast 2016. All Rights Reserved.
 * <p/>
 * http://www.vast.com
 */
package com.vocumsineratio.kata.probability;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Danil Suits (danil@vast.com)
 */
public class Tests {
    /*
        Write a probability value object. It should contain the following methods:
           Probability CombinedWith(Probability)
           Probability InverseOf()
           Probability Either(Probability)

        if you forget your probability math:
           Either:P(A) + P(B) - P(A)P(B)
           CombinedWith: P(A)P(B)
           InverseOf: 1 - P(A)

        The math is surprisingly not the main part of the exercise.

        The internal state should be held as a decimal. Use TDD when writing this object.

        One last thing. You can't put any getters on the object.  What's the first test to write?

    */

    @Test
    public void extendHappensTwice() {
        // Bootstrapping begins with declaring the interface that you want to have.

        // Part of the problem is that we have an implementation goal without a business
        // goal; so we need to invent a domain problem that we think will lead to
        // the probability implementation above.  So I flipped a coin.

        
        double result = CoinTossing.bothCoinsLandHeads();
    }

    @Test
    public void bothCoinsLandHeads() {
        double result = CoinTossing.bothCoinsLandHeads();
        final double expectedResult = .25;
        Assert.assertEquals(result, expectedResult);
    }
}
