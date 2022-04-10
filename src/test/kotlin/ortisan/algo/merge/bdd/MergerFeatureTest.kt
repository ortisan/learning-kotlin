package ortisan.algo.merge.bdd

import io.cucumber.junit.CucumberOptions
import io.cucumber.junit.platform.engine.Cucumber

@Cucumber
@CucumberOptions(
    features = ["classpath:ortisan.algo.merge.bdd"],
    glue = ["classpath:ortisan.algo.merge.bdd"],
    plugin = ["pretty", "html:target/cucumber/html"]
)
class MergerFeatureTest