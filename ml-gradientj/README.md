# MLGradientJ

Machine learning algorithms from scratch in pure Java. No dependencies, no magic.

Built for deep understanding, one algorithm at a time — starting with linear regression trained by batch gradient descent.

## Run the demo

```bash
mvn compile exec:java -Dexec.mainClass=io.github.vishnuvardhand.mlgradientj.Main
# or without the exec plugin:
mvn package && java -cp target/classes io.github.vishnuvardhand.mlgradientj.Main
```

## Roadmap

- [x] Linear regression (batch gradient descent, MSE)
- [ ] Logistic regression
- [ ] K-nearest neighbors
- [ ] More to come
