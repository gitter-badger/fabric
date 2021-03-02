package hierarchical.parse
import com.typesafe.config.{ConfigFactory, ConfigRenderOptions}
import hierarchical.Value

object Hocon extends Parser {
  override def parse(content: String): Value = {
    val conf = ConfigFactory.parseString(content).resolve()
    val jsonString = conf.root().render(ConfigRenderOptions.concise())
    Json.parse(jsonString)
  }
}
