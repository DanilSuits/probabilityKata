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
public class TestSpikeTwo {
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
    public void checkThatProbablityImplementsCorrectSignatures() {
        Probability p = new Probability();
        Probability q = p.inverseOf();
        Probability r = p.either(q);
        Probability s = p.combinedWith(q);
    }

    @Test
    public void checkThatNegationIfNegationIsIdentity () {
        Probability p = new Probability();
        Assert.assertEquals(p.inverseOf().inverseOf(), p);

        Probability q = new Probability();
        Assert.assertEquals(q.inverseOf().inverseOf(), q);

        Probability r = p.either(q);
        Assert.assertEquals(r.inverseOf().inverseOf(), r);

        Probability s = p.combinedWith(q);
        Assert.assertEquals(s.inverseOf().inverseOf(), s);
    }

    static class Probability {

        public Probability inverseOf() {
            return this;
        }

        public Probability either(Probability q) {
            return this;
        }

        public Probability combinedWith(Probability q) {
            return this;
        }
    }
}
