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

    interface Probability<P extends Probability<P>> extends Value<P> {
        P inverseOf();
        P either(P other);
        P combinedWith(P other);
    }

    @Test(dataProvider = "examples")
    public <V extends Value<V>> void checkValueContract(V start) {
        Assert.assertTrue(start.sameValueAs(start));
        Assert.assertFalse(start.sameValueAs(null));
    }

    @Test(dataProvider = "examples")
    public <P extends Probability<P>> void checkIdentity(P start) {
        checkEquals(start, start);
    }

    @Test(dataProvider = "examples")
    public <P extends Probability<P>> void checkInverse(P start) {
        final Probability<P> other = start.inverseOf();
        checkEquals(other.inverseOf(), start);
    }

    @Test(dataProvider = "examples", enabled = false)
    public <P extends Probability<P>> void checkCombine(P start) {
        final P other = start.inverseOf();
        checkEquals(other.combinedWith(start), start.combinedWith(other));
    }

    private <P extends Probability<P>> void checkEquals(P actual, P expected) {
        Assert.assertNotNull(expected);
        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.sameValueAs(expected));
        Assert.assertTrue(expected.sameValueAs(actual));
    }


    @DataProvider(name = "examples")
    public Object[][] createTestDoubles () {
        return new Object[][]{{ new TestDouble(.6) }};
    }

    final static class TestDouble implements Probability<TestDouble> {
        // The internal state should be held as a decimal
        private final double v;

        TestDouble(double v) {
            this.v = v;
        }

        public TestDouble inverseOf() {
            return new TestDouble(1 - this.v);
        }

        public TestDouble either(TestDouble other) {
            return null;  //TODO: To change body of implemented methods use File | Settings | File Templates.
        }

        public TestDouble combinedWith(TestDouble other) {
            return null;  //TODO: To change body of implemented methods use File | Settings | File Templates.
        }

        public boolean sameValueAs(TestDouble other) {
            if (null == other) return false;
            return this.v == other.v;
        }
    }
}
