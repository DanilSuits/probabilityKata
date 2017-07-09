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

## REFACTOR

Extracting the core of the work into a function.  The name combine
being taken from the ubiquitous language -- it other words, chosen
to match the interface that we're supposed to create in this exercise.

There are two important things to note here; first, that the signature
of this method is expressed entirely in Probabilities.  We've left the
boundary data behind.  And second, that the method shows us that this
invocation of the probability type is anemic; we're reaching into the
data structure to extract values and put them back.

## REFACTOR

Here's the non-anemic version: we leave the data inside the probability
type, and let it do its job.

## REFACTOR

Since combine(Probability, Probability) is now redundant, we can get rid
of it as easily as we introduced it.

## REFACTOR

We can play the same game with the return value; extracting the property
to a separate method.

## REFACTOR

Once again, we can move the method so that it is closer to the underlying
data.

## REFACTOR: changing the data type.

In part two of the probability kata, Greg challenges us to modify the
way we store the probability state.  The current design makes that really
straight forward - the decision on how to represent the probability in
memory isolated within a single module, so we can change that piece by
itself, and the tests still pass.

This owes a lot to the fact that I've been stingy about which decisions
are part of the "public API"; the would not have worked if the API
included direct access to the member variable itself.  Introducing
Probabilty.toDouble() is a key step in keeping everything clean.

toDouble() makes explicit the distinction between communicating the
state and representing the state.

## REFACTOR

I happened to notice a minor inefficiency in Probability.combinedWith()
which happens to be using the public API to instantiate the result.  That's
not right; the point of being able to switch to floats from doubles is
to preserve efficiency.

So we introduce a new constructor, to handle float to float more correctly.
A quick check in the IDE shows me that the correct constructor is being
invoked.

## REFACTOR

And then we implement the original constructor in terms of the new one.

## REFACTOR

At this point,  I want to start making the distinction between the
data model and the domain model a bit more explict; there's some
coupling between the two that isn't yet quite obvious, and so I'm
going to try to surface it.

This is all still happening behind the facade; until we make an
explicit decision to lift these concepts into the API, they remain
mere implementation details.

## REFACTOR

Extracting the domain logic into the domain model is a straight forward
exercise; basically we just grab everything that doesn't need to know
about state, and move it together.

It still looks a little bit clumsy, because we're working in a toy
problem.  I think it would be easier to see where the boundaries if
there were multiple types of things involved.

## REFACTOR

Actually, lets run with that.  I keep using the word "coin", so it
really ought to show up in code somewhere.  That also helps me to
make explicit the fact that I'm talking about a _fair_ coin.

Disclaimer: I had no idea that was coming.  If I had, I probably would
have contrived to introduce the idea earlier.

## REFACTOR

Introducing the Coin into the domain model suggests a bunch of
spelling changes, so I run with those.  It's beginning to feel
less like a toy problem.

## REFACTOR

It seems there's a lot of room for cleanup of the language within
the model.  I seem to be conflating coin and toss; probability and
event.  I suspect deeper teasing might expose a distribution as
well.  The rabbit hole goes deeper; the cutoff in this exercise
is arbitrary -- yagni, perhaps.  We aren't trying to discover a
model of everything from a single use case.

## REFACTOR

Applying extract method to create an instance of a fair coin.
