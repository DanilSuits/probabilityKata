/**
 * Copyright Vast 2016. All Rights Reserved.
 * <p/>
 * http://www.vast.com
 */
package com.vocumsineratio.kata.probability;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
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

    interface Probability<P extends Probability<P>> {
        Probability<P> inverseOf();
        Probability<P> either(Probability<P> other);
        Probability<P> combinedWith(Probability<P> other);
    }

    @Test(dataProvider = "examples")
    public <P extends Probability<P>> void checkInverse(Probability<P> start) {
        Assert.assertEquals(start.inverseOf().inverseOf(), start);
    }

    @DataProvider(name = "examples")
    public Object[][] createTestDoubles () {
        return new Object[][]{{null}};
    }
}
