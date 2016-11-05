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
import java.util.EnumSet;
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
    public <P extends Probability<P>> void checkNegationOfNegationIsIdentity(P initialSeed) {
        checkSameValueAs(initialSeed.inverseOf().inverseOf(), initialSeed);
    }

    @Test(dataProvider = "unaryProbabilityProvider")
    public <P extends Probability<P>> void checkEither(P initialSeed) {
        checkValueNotSameAsNull(initialSeed.either(initialSeed));

        checkDeMorgansLaws(initialSeed, initialSeed);
    }

    @Test(dataProvider = "binaryProbabilityProvider")
    public <P extends Probability<P>> void checkDeMorgansLaws(P a, P b) {
        {
            // the negation of a disjunction
            P and = a.combinedWith(b);
            P negationOfDisjunction = and.inverseOf();

            // is the conjunction of the negations
            P notA = a.inverseOf();
            P notB = b.inverseOf();
            P conjunctionOfNegations = notA.either(notB);

            checkSameValueAs(negationOfDisjunction, conjunctionOfNegations);
        }

        {
            // The negation of a conjunction
            P or = a.either(b);
            P negationOfConjunection = or.inverseOf();

            // is the disjunction of the negations
            P notA = a.inverseOf();
            P notB = b.inverseOf();
            P disjunctionOfNegations = notA.combinedWith(notB);

            checkSameValueAs(negationOfConjunection, disjunctionOfNegations);
        }

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

        List<Probability> samples = Lists.<Probability>newArrayList
                ( DoubleProbability.from(.625)
                , DoubleProbability.from(.6)
                , BooleanProbability.TRUE
                , SingularProbability.ONE
                , MontyPython.ALL
                , MontyPython.TIME_TO_THROW
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

    @DataProvider(name = "binaryProbabilityProvider")
    public Iterator<Object []> binaryProbabilityProvider () {
        List<Object []> samples = Lists.<Object []>newArrayList
                ( new Object[] {DoubleProbability.from(.625), DoubleProbability.from(.6)}
                , new Object[] {BooleanProbability.TRUE, BooleanProbability.FALSE}
                );

        return samples.iterator();
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

        checkSameValueAs(DoubleProbability.from(noRoundingError), DoubleProbability.from(withRoundingError));
    }

    enum MontyPython {
        ONE, TWO, FIVE, THREESIR;

        static final EnumSetProbability<MontyPython> from (EnumSet<MontyPython> elements) {
            return new EnumSetProbability<MontyPython>(MontyPython.class, elements);
        }

        static final EnumSetProbability<MontyPython> from (MontyPython first, MontyPython... rest) {
           return from (EnumSet.of(first,rest));
        }
        static final EnumSetProbability<MontyPython> ALL = from(EnumSet.allOf(MontyPython.class));
        static final EnumSetProbability<MontyPython> TIME_TO_THROW = from(ONE, TWO, FIVE);
    }

    final static class EnumSetProbability<E extends Enum<E>> implements Probability<EnumSetProbability<E>> {
        private final Class<E> theClass;
        private final EnumSet<E> elements;

        EnumSetProbability(Class<E> theClass, EnumSet<E> elements) {
            this.theClass = theClass;
            this.elements = elements;
        }

        public EnumSetProbability<E> from (EnumSet<E> source) {
            return new EnumSetProbability<E>(theClass, source.clone());
        }

        public EnumSetProbability inverseOf() {
            return from(EnumSet.complementOf(elements));
        }

        public EnumSetProbability combinedWith(EnumSetProbability that) {
            // negation of the conjunction of the negation....
            return this.inverseOf().either(that.inverseOf()).inverseOf();
        }

        public EnumSetProbability either(EnumSetProbability that) {
            EnumSet<E> source = elements.clone();
            source.addAll(that.elements);

            return from(source);
        }

        public boolean sameValueAs(EnumSetProbability that) {
            if (null == that) return false;

            return this.elements.equals(that.elements);
        }
    }

    final static class BooleanProbability implements Probability<BooleanProbability> {
        private final boolean internalState;

        BooleanProbability(boolean internalState) {
            this.internalState = internalState;
        }

        public static BooleanProbability TRUE =  new BooleanProbability(true);
        public static BooleanProbability FALSE = new BooleanProbability(false);

        static BooleanProbability from(boolean state) {
            return state ? TRUE : FALSE;
        }

        public BooleanProbability inverseOf() {
            return from(! internalState);
        }

        public BooleanProbability combinedWith(BooleanProbability other) {
            return from(internalState && other.internalState);
        }

        public BooleanProbability either(BooleanProbability other) {
            return from( internalState || other.internalState);
        }

        public boolean sameValueAs(BooleanProbability other) {
            return this == other;
        }
    }

    final static class SingularProbability implements Probability<SingularProbability> {
        public static SingularProbability ONE = new SingularProbability();

        public SingularProbability inverseOf() {
            return this;
        }

        public SingularProbability combinedWith(SingularProbability other) {
            return this;
        }

        public SingularProbability either(SingularProbability other) {
            return this;
        }

        public boolean sameValueAs(SingularProbability other) {
            return this == other;
        }
    }

    abstract static class DeMorganProbability<P extends DeMorganProbability<P>> implements Probability<P> {
        final public P either(P that) {
            P notA = this.inverseOf();
            P notB = that.inverseOf();
            P disjunctionOfNegation = notA.combinedWith(notB);
            P negationOfDisjunctionOfNegation = disjunctionOfNegation.inverseOf();

            return negationOfDisjunctionOfNegation;
        }
    }

    final static class DoubleProbability extends DeMorganProbability<DoubleProbability> {
        // The internal state should be held as a decimal
        private final double v;

        DoubleProbability(double v) {
            this.v = v;
        }

        public DoubleProbability inverseOf() {
            return DoubleProbability.from(1 - this.v);
        }

        public DoubleProbability combinedWith(DoubleProbability other) {
            return DoubleProbability.from(this.v * other.v);
        }

        public boolean sameValueAs(DoubleProbability that) {
            if (null == that) return false;

            return Comparison.closeEnough(this, that);
        }

        private static class Comparison {
            // Positive values only please.
            // This choice is an arbitrary one.
            static final double TOLERANCE = .00001;

            static boolean closeEnough(DoubleProbability lhs, DoubleProbability rhs) {
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

        public static DoubleProbability from(double v) {
            return new DoubleProbability(v);
        }
    }
}
