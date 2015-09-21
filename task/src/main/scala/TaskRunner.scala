import model.Card
import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import org.jsoup.nodes.Element

object TaskRunner extends skinny.task.TaskLauncher {

  register("assets:precompile", (params) => {
    val buildDir = params.headOption.getOrElse("build")
    skinny.task.AssetsPrecompileTask.main(Array(buildDir))
  })

  register("loadCards", _ => {
    val b = new Browser
    val doc = b.get("http://magic.wizards.com/ja/articles/archive/card-image-gallery/%E3%80%8E%E6%88%A6%E4%B9%B1%E3%81%AE%E3%82%BC%E3%83%B3%E3%83%87%E3%82%A3%E3%82%AB%E3%83%BC%E3%80%8F-2015-09-17")
    // println(doc)
    val imageTags: Seq[Element] =
      doc >> elementList("div#content-detail-page-of-an-article > div > div > p > img")
    val nameAndImageUrls = imageTags.map(tag => (tag.attr("alt"), tag.attr("src")))
    // println(nameAndImageUrls)

    skinny.DBSettings.initialize()
    Card.deleteAll
    nameAndImageUrls.foreach { p =>
      Card.createWithAttributes('name -> p._1, 'cardNumber -> 1, 'imageUrl -> p._2)
    }
    // Card.findAll().foreach(c => println(c))
  })

}

