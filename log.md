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
