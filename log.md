# Probability Kata

## RED: extending the API

### What's the first test to write?

In order to test first a probability implementation we
need some sort of domain that can use one.  Coin flipping
is a pretty naive domain, but has some really nice advantages.
The domain isn't the point of the exercise anyway.

We don't have any API yet, so the only valid move in the 
test first game is to extend the API: define the API in a
test without restricting its behavior.

We get a RED bar, of course, because the test code doesn't
compile.

## GREEN: extending the API

Automatic code generation does all the work to create the
class.  

My IDE doesn't have an intuitive interface for
creating the new module in a new package, which is a little
bit unfortunate.  On the other hand, refactoring an
implementation into a new package using my rules would
be an interesting exercise.

The new method is public by default; for this discipline
I probably want new methods to have more restricted access.
Which is to say, I want putting things into the public API
to be a deliberate act, rather than a default.  So I need
to see later if I can adjust the templates to manage it.

## RED

Now that I have an API, I can constrain the value.

The second test subsumes the first, but I can't get rid
of the second test because the first one isn't passing yet.

The use of a primitive for the expected value here is not
an accident.  The test code is exercising how the implementation
works at the boundary, because that's where the constraint
is -- the internal workings of the model are internal.

Furthermore, this is all the API surface we have at this
point, so we get to live with it.  That's important; the
tests are communicating to the author that the interface 
isn't expressive.

## GREEN 

The simplest thing that could possibly work!

My discipline is this: if the tests are green, then I
can _ship_ if the tests are complete.  

A coin tossing module that can only compute a single 
result isn't going to add a lot of business value, 
but any code that links to this library is going to 
get the right answer.

This is, I think, an important trick to keep in mind;
that if we have some new behavior that we need, and aren't
quite sure how it goes with the current implementation,
one possibility is to create a new entry point in the api
that is specialized to that use case, then grind on
removing the duplication.

## REFACTOR

First refactoring is to put add context to the production
code.  In other words, we're expressing explicitly in the
code what is going on.

This is a key idea I missed when I first experimented with
TDD many years ago.  At the time, _duplication_ was
understood to be the key trigger for refactoring.  The
abstractions were supposed to be understood from first
discovering that something was duplicated, and then acting
on it.

Kent Beck had a reputation for being able to see duplication
where others would miss it.  I think what was really
happening was that he was going after the abstractions
earlier, by making meaning explicit earlier in the
process.

In this case, there is duplication between the test code
and the production code; they each have a separate and
distinct understanding of coin flipping that happen to 
agree, and happen to be represented the same way.

### Eliding tests

Since both tests are green, and I can establish by inspection
that the second test covers the first, I could elide
the initial test.

Unfortunately, what I didn't think about at the time
of writing the second test (which I'm treating as immutable),
is that eliding the test takes the explanatory comment with
it.

In a more rational discpline, I'd just move the comment
and nuke the original test.

## REFACTOR

The name of the result is real, but the meaning of it was
implicit; 0.25 is the right answer because it represents
0.5 multiplied by itself.

So following Kent's lead, we tease that piece out.

## REFACTOR

At this point, wanting to get on with it, I go after primitive
obsession; "Probability" is the concept, double just happens to 
be the representation.

So the concept is made explicit.  It doesn't go into the public
API yet, of course; partly because it isn't done, partly because
_you aren't going to need it_, but mostly because we're still
exploring the space.

We know, from the problem definition, that the concept of
probability eventually escapes from this test.  But playing by
the rules, we would want to keep our experiments within a tight
scope until we had accumulated some evidence that they are
fit for purpose.

## REFACTOR

In this step, I realized that the language was really calling
for combining two different events, that happen to have the
same outcome; so I introduced an explicit first and second.

Confessions: the refactoring came first, then the justification
followed.  We know, from the problem statement of the kata, that
we work toward "CombinedWith", which accepts a second probability 
as an argument.

A better problem to model would have been the odds of drawing
a black ace from a deck of playing cards.  Such is life.

## REFACTOR

The outcome is also a probability, so let's make that explicit as
well.
