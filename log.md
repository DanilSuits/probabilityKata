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

