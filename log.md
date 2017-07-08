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

