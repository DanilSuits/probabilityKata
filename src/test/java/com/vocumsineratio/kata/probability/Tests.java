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
        final Probability<P> other = start.inverseOf();
        final Probability<P> actual = other.inverseOf();
        final Probability<P> expected = start;
        Assert.assertEquals(actual, expected);
    }


    @DataProvider(name = "examples")
    public Object[][] createTestDoubles () {
        return new Object[][]{{ new TestDouble(.6) }};
    }

    static class TestDouble implements Probability<TestDouble> {
        // The internal state should be held as a decimal
        private final double v;

        TestDouble(double v) {
            this.v = v;
        }

        public Probability<TestDouble> inverseOf() {
            return new TestDouble(1 - this.v);
        }

        public Probability<TestDouble> either(Probability<TestDouble> other) {
            return null;  //TODO: To change body of implemented methods use File | Settings | File Templates.
        }

        public Probability<TestDouble> combinedWith(Probability<TestDouble> other) {
            return null;  //TODO: To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            TestDouble rhs = TestDouble.class.cast(obj);
            if (null == rhs) return false;
            return this.v == rhs.v;
        }
    }
}
