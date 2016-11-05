/**
 * Copyright Vast 2016. All Rights Reserved.
 * <p/>
 * http://www.vast.com
 */
package com.vocumsineratio.kata.probability;

import com.beust.jcommander.internal.Lists;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
    public <V extends Value<V>> void checkValueSameAsItself(V initialSeed) {
        checkSameValueAs(initialSeed, initialSeed);
    }

    private <V extends Value<V>> void checkSameValueAs(V actual, V expected) {
        Assert.assertTrue(actual.sameValueAs(expected));
        Assert.assertTrue(expected.sameValueAs(actual));
    }

    interface Probability<P extends Probability<P>> extends Value<P> {
        P inverseOf();
        P combinedWith(P other);
        P either(P other);
    }

    @Test(dataProvider = "unaryProbabilityProvider")
    public <P extends Probability<P>> void checkInverse(P initialSeed) {
        checkSameValueAs(initialSeed.inverseOf().inverseOf(), initialSeed);
    }

    @Test(dataProvider = "unaryProbabilityProvider")
    public <P extends Probability<P>> void checkEither(P initialSeed) {
        checkValueNotSameAsNull(initialSeed.either(initialSeed));
    }

    @Test(dataProvider = "unaryProbabilityProvider")
    public <P extends Probability<P>> void checkCombine(P initialSeed) {
        final P other = initialSeed.inverseOf();
        checkSameValueAs(other.combinedWith(initialSeed), initialSeed.combinedWith(other));
    }

    @Test(dataProvider = "unaryProbabilityProvider")
    public <P extends Probability<P>> void checkCombineIsAssociative(P initialSeed) {
        checkCombineIsAssociative(initialSeed, initialSeed, initialSeed);

        final P other = initialSeed.inverseOf();
        checkCombineIsAssociative(other, initialSeed, initialSeed);
        checkCombineIsAssociative(initialSeed, other, initialSeed);
        checkCombineIsAssociative(initialSeed, initialSeed, other);

    }

    private <P extends Probability<P>> void checkCombineIsAssociative(P a, P b, P c) {
        P ab = a.combinedWith(b);
        P bc = b.combinedWith(c);

        checkSameValueAs(a.combinedWith(bc), ab.combinedWith(c));
    }

    @DataProvider(name = "unaryProbabilityProvider")
    public Iterator<Object []> unaryProbabilityProvider() {
        final TestDouble exactTestDouble = TestDouble.from(.625);

        List<Probability> samples = Lists.<Probability>newArrayList
                (exactTestDouble
                , TestDouble.from(.6)
                ) ;

        List<Object []> derivedTests = Lists.newArrayList();
        for(Probability current : samples) {
            final List<Probability> unaryStates = Arrays.asList
                    ( current
                    , current.inverseOf()
                    );

            for (Probability initialState : unaryStates) {
                derivedTests.add(new Object[]{initialState});
            }
        }

        return derivedTests.iterator();
    }

    // This test is specific to TestDouble
    @Test
    public void testFloatingPointMathProblems () {
        // Here's my arbitrarily chosen probability
        // It has the lucky property that initialSeed * initialSeed
        // is not exactly expressable as a double.
        final double initialSeed = .4;

        // And the same negation I was using all along
        double other = 1 - initialSeed;

        double noRoundingError =  initialSeed * (initialSeed * other);
        double withRoundingError = (initialSeed * initialSeed) * other;

        checkSameValueAs(TestDouble.from(noRoundingError), TestDouble.from(withRoundingError));

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

        public boolean sameValueAs(TestDouble that) {
            if (null == that) return false;

            return Comparison.closeEnough(this, that);
        }

        private static class Comparison {
            // Positive values only please.
            // This choice is an arbitrary one.
            static final double TOLERANCE = .00001;

            static boolean closeEnough(TestDouble lhs, TestDouble rhs) {
                return closeEnough (lhs.v, rhs.v);
            }

            static boolean closeEnough(double lhs, double rhs) {
                double error = lhs - rhs;

                // If lhs != rhs, then one of these will be negative
                // and the other positive branch will be big or small.
                return isNegligible(error) && isNegligible(- error);
            }

            static boolean isNegligible(double error) {
                return error < TOLERANCE;
            }
        }

        @Override
        public String toString() {
            return (new StringBuilder())
                    .append("{ ")
                    .append(getClass().getSimpleName())
                    .append(" : ")
                    .append(this.v)
                    .append(" }")
                    .toString();
        }

        public static TestDouble from(double v) {
            return new TestDouble(v);
        }
    }
}
