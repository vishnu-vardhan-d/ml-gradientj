# MLGradientJ

Machine learning algorithms from scratch in pure Java. No dependencies, no magic.

Built for deep understanding, one algorithm at a time — starting with linear regression
trained by gradient descent.

**[▶ Open the interactive explainer](https://vishnu-vardhan-d.github.io/ml-gradientj/)** —
step through the training one example at a time and watch the model correct itself.

---

## The problem it solves

A lorry leaves with a load. The paperwork says it will clear a certain profit — but that
figure only counts the diesel going *out*. Coming home it may find another load, part of
one, or nothing at all. **When it runs home empty, that fuel is burned for no income.**

Every route wastes a different share of its return diesel, and nobody writes that share
down. The program works it out from journeys that have already finished.

```
model      predict = planned − (weight × diesel)
unknown    weight — the share of return diesel that earns nothing
method     predict, measure the miss, nudge the weight, repeat
```

## What it looks like running

```
Pass  1: weight=  7.89%, moved= 7.89 points
Pass  2: weight= 14.31%, moved= 6.42 points
Pass  3: weight= 19.54%, moved= 5.23 points
...
Pass 37: weight= 42.50%, moved= 0.00 points

Settled after 37 passes at 42.50%
```

Notice the moves shrinking. Nothing slows it down deliberately — the correction is always a
slice of the disagreement, so as the guess approaches the truth the corrections die out on
their own. That is what "converging" means.

## The whole algorithm

Five lines, inside two loops:

```java
double predict = trip.planned - (weight * trip.diesel);   // what the model expects
double miss    = predict - trip.actual;                   // how wrong, in rupees
double gap     = miss / trip.diesel;                      // the same miss, as a rate
double error   = STEP_SIZE * gap;                         // move only part of the way
weight += error;                                          // the learning
```

- **inner loop** — one journey, one nudge
- **outer loop** — one *pass* (an epoch) over every journey, repeated until the weight
  stops moving

## Two things that are easy to get wrong

**Units.** `miss` is in rupees; `weight` is a fraction. You cannot add one to the other.
Dividing by `diesel` is what converts between them — that single division is the bridge.

**Step size.** Act on the *whole* disagreement and the weight simply lands on whatever the
last journey said, then leaps somewhere else on the next one, never settling. Taking a slice
lets every journey pull, and the weight comes to rest where their pulls balance.

## The explainer page

`docs/index.html` — a single self-contained file, no build step. It covers:

- **Fundamentals** — what "regression" and "linear" actually mean, and where this sits in
  the family of machine-learning algorithms
- **Gradient descent** — an interactive hill showing how wrong *every* possible guess would
  be, and why you cannot simply scan it once there is more than one unknown
- **A calculator** — change the numbers and watch a profitable-looking journey turn into a loss
- **Step-by-step training** — feed one journey at a time, or run a whole pass, and see the
  full working: prediction, miss, gap, correction, new weight
- **The comparison** — how this differs from decision trees and gradient boosting, and why
  the real trade is explainability rather than accuracy

Every number on the page is computed live in the browser from the same journeys the Java
uses. Nothing is hard-coded.

## Running it

```bash
cd ml-gradientj
mvn compile exec:java -Dexec.mainClass=io.mlgradientj.LinearRegression

# or without the exec plugin
mvn package && java -cp target/classes io.mlgradientj.LinearRegression
```

## Roadmap

- [x] Linear regression (gradient descent)
- [x] Interactive explainer with the full working shown
- [ ] Logistic regression
- [ ] Decision tree
- [ ] The same visual treatment for each, so the differences are visible rather than described

## An honest note

The journey outcomes here are illustrative — composed to demonstrate the algorithm, not
collected from real lorries. That does not change the maths, and the pipeline is exactly the
one you would run on real reports. But a model is only ever as truthful as the data
underneath it, and this repository would rather say so than imply otherwise.

## Licence

MIT
