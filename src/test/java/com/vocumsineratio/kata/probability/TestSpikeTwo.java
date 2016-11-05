/**
 * Copyright Vast 2016. All Rights Reserved.
 * <p/>
 * http://www.vast.com
 */
package com.vocumsineratio.kata.probability;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

    @Test(dataProvider = "unaryProvider")
    public void checkThatProbablityImplementsCorrectSignatures(Probability p) {
        Probability q = p.inverseOf();
        Probability r = p.either(q);
        Probability s = p.combinedWith(q);
    }

    @Test(dataProvider = "unaryProvider")
    public void checkThatNegationIfNegationIsIdentity(Probability p) {
        Assert.assertEquals(p.inverseOf().inverseOf(), p);
    }

    @Test(dataProvider = "binaryProvider")
    public void checkThatNegationOfNegationIsIdentity(Probability p, Probability q) {
        Probability r = p.either(q);
        Assert.assertEquals(r.inverseOf().inverseOf(), r);

        Probability s = p.combinedWith(q);
        Assert.assertEquals(s.inverseOf().inverseOf(), s);
    }

    @Test(dataProvider = "binaryProvider")
    public void checkThatDeMorgansLawsAreSatisified(Probability p, Probability q) {
        {
            // The negation of the disjunction is the conjunction of the negations
            Probability disjunction = p.combinedWith(q);
            Probability negationOfDisjunction = disjunction.inverseOf();

            Probability notP = p.inverseOf();
            Probability notQ = q.inverseOf();
            Probability conjunctionOfNegations = notP.either(notQ);

            Assert.assertEquals(negationOfDisjunction, conjunctionOfNegations);
        }

        {
            // The negation of the conjunction is the disjunction of the negations
            Probability conjunction = p.either(q);
            Probability negationOfTheConjunction = conjunction.inverseOf();

            Probability notP = p.inverseOf();
            Probability notQ = q.inverseOf();
            Probability disjunctionOfNegations = notP.combinedWith(notQ);

            Assert.assertEquals(negationOfTheConjunction, disjunctionOfNegations);
        }
    }

    @Test(dataProvider = "binaryProvider")
    public void checkThatProbabilitiesRespectSymmetry(Probability p, Probability q) {
        Assert.assertEquals(p.combinedWith(q), q.combinedWith(p));
    }

    @DataProvider
    public Iterator<Object[]> unaryProvider() {
        List<Object[]> trials = Arrays.<Object[]>asList(new Object[]{new Probability()});
        return trials.iterator();
    }

    @DataProvider
    public Iterator<Object[]> binaryProvider() {
        List<Object[]> trials = Arrays.<Object[]>asList(new Object[]{new Probability(), new Probability()});
        return trials.iterator();
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
