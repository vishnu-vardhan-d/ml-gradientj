# MLGradientJ

**Machine learning algorithms written from scratch in Java. No libraries, no magic — just the arithmetic.**

Derived on paper first, then coded from that derivation. Every prediction, every mistake and
every correction is visible, so you can follow along and check the numbers yourself.

### [▶ Open the interactive explainer](https://vishnu-vardhan-d.github.io/ml-gradientj/)

<!-- Add a screenshot or GIF of the explainer page here -- it is the single biggest
     improvement you can make to this README. See "Adding the screenshot" below. -->
![The interactive explainer](docs/screenshot.png)

---

## The entire algorithm

Linear regression, trained by gradient descent. This is all of it:

```java
double predict = trip.planned - (weight * trip.diesel);   // what the model expects
double miss    = predict - trip.actual;                   // how wrong, in rupees
double gap     = miss / trip.diesel;                      // the same miss, as a rate
double error   = STEP_SIZE * gap;                         // move only part of the way
weight += error;                                          // the learning
```

Five lines inside two loops — one journey at a time, one sweep at a time, until the number
stops moving.

```
Pass  1: weight=  7.89%, moved= 7.89 points
Pass  2: weight= 14.31%, moved= 6.42 points
Pass  3: weight= 19.54%, moved= 5.23 points
...
Pass 37: weight= 42.50%, moved= 0.00 points

Settled after 37 passes at 42.50%
```

Watch the moves shrink. Nothing slows it down deliberately — the correction is always a
slice of the disagreement, so as the guess nears the truth the corrections die out on their
own. **That is what "converging" means**, and you can see it happening.

---

## The problem it solves

A lorry leaves with a load. The paperwork promises a certain profit — but that only counts
the diesel going *out*. Coming home it may find another load, part of one, or nothing at all.
**When it runs home empty, that fuel is burned for no income.**

Every route wastes a different share of its return diesel, and **nobody writes that share
down**. So the program works it out from journeys that have already finished.

```
model      predict = planned − (weight × diesel)
unknown    weight — the share of return diesel that earns nothing
method     predict, measure the miss, nudge the weight, repeat
```

---

## Two things that are easy to get wrong

**Units.** `miss` is in rupees; `weight` is a fraction. You cannot add one to the other.
Dividing by `diesel` is what converts between them — that single division is the bridge, and
getting it wrong makes the weight explode.

**Step size.** Act on the *whole* disagreement and the weight simply lands on whatever the
last journey said, then leaps elsewhere on the next one, never settling. Taking a slice lets
every journey pull, and the weight comes to rest where their pulls balance — which is their
average.

---

## The explainer page

A single self-contained HTML file. No build step, no dependencies, nothing to install.

| | |
|---|---|
| **Fundamentals** | what "regression" and "linear" actually mean, and where this sits among the other algorithms |
| **Gradient descent** | an interactive hill showing how wrong *every* possible guess would be — and why you cannot simply scan it once there is more than one unknown |
| **Calculator** | change the numbers and watch a profitable-looking journey turn into a loss |
| **Step-by-step training** | feed one journey at a time, or run a whole pass, with the full working shown: prediction, miss, gap, correction, new weight |
| **Comparison** | how this differs from decision trees and gradient boosting, and why the real trade is explainability rather than accuracy |

Every number on the page is computed live in the browser from the same journeys the Java
uses. Nothing is hard-coded.

---

## Running it

```bash
cd ml-gradientj
mvn compile exec:java -Dexec.mainClass=io.mlgradientj.LinearRegression

# or without the exec plugin
mvn package && java -cp target/classes io.mlgradientj.LinearRegression
```

Requires Java 17+ and Maven. The explainer page needs neither — just open
`docs/index.html` in a browser.

---

## Roadmap

- [x] **Linear regression** — gradient descent, from scratch
- [x] **Interactive explainer** — every step of the working, visible
- [ ] **Logistic regression** — predicting a yes/no instead of a number
- [ ] **Decision tree** — learned if/then rules instead of a straight line
- [ ] The same visual treatment for each, so the differences are *shown* rather than described

---

## An honest note

The journey outcomes here are illustrative — composed to demonstrate the algorithm, not
collected from real lorries. That does not change the maths, and the pipeline is exactly the
one you would run on real reports. But a model is only ever as truthful as the data beneath
it, and this repository would rather say so than imply otherwise.

## Licence

MIT
