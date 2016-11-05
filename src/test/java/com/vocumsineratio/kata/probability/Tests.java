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

    interface Value<V extends Value<V>> {
        boolean sameValueAs(V other);
    }

    @Test(dataProvider = "unaryProbabilityProvider")
    public <V extends Value<V>> void checkValueNotSameAsNull(V initialSeed) {
        Assert.assertFalse(initialSeed.sameValueAs(null));
    }

    @Test(dataProvider = "unaryProbabilityProvider")
    public <V extends Value<V>> void checkValueIdentity(V initialSeed) {
        checkSameValueAs(initialSeed, initialSeed);
    }

    private <V extends Value<V>> void checkSameValueAs(V actual, V expected) {
        Assert.assertTrue(actual.sameValueAs(expected));
        Assert.assertTrue(expected.sameValueAs(actual));
    }

    interface Probability<P extends Probability<P>> extends Value<P> {
        P inverseOf();
        P either(P other);
        P combinedWith(P other);
    }

    @Test(dataProvider = "unaryProbabilityProvider")
    public <P extends Probability<P>> void checkInverse(P initialSeed) {
        checkSameValueAs(initialSeed.inverseOf().inverseOf(), initialSeed);
        final P other = initialSeed.inverseOf();
        checkSameValueAs(other.inverseOf().inverseOf(), other);
    }

    @Test(dataProvider = "unaryProbabilityProvider")
    public <P extends Probability<P>> void checkCombine(P initialSeed) {
        final P other = initialSeed.inverseOf();
        checkSameValueAs(other.combinedWith(initialSeed), initialSeed.combinedWith(other));
    }

    @DataProvider(name = "oneArg")
    public Object[][] unaryProbabilityProvider() {
        return new Object[][]{{ TestDouble.from(.6) }};
    }

    final static class TestDouble implements Probability<TestDouble> {
        // The internal state should be held as a decimal
        private final double v;

        TestDouble(double v) {
            this.v = v;
        }

        public TestDouble inverseOf() {
            return TestDouble.from(1 - this.v);
        }

        public TestDouble combinedWith(TestDouble other) {
            return TestDouble.from(this.v * other.v);
        }

        public TestDouble either(TestDouble other) {
            return null;  //TODO: To change body of implemented methods use File | Settings | File Templates.
        }

        public boolean sameValueAs(TestDouble other) {
            if (null == other) return false;
            return this.v == other.v;
        }

        public static TestDouble from(double v) {
            return new TestDouble(v);
        }
    }
}
