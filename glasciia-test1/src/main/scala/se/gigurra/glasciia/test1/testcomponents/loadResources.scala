package se.gigurra.glasciia.test1.testcomponents

import com.badlogic.gdx.graphics.{Color, Pixmap}
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.{BitmapFont, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import se.gigurra.glasciia.Glasciia._
import se.gigurra.glasciia._
import se.gigurra.math.{Box2, Vec2}

/**
  * Created by johan on 2016-10-08.
  */
object loadResources {

  private def createTextureRegionLoader(app: App): Loader.InMemory[TextureRegion] = {
    val out = TextureRegionLoader.createNew()
    app.addResource("texture-loader", out)
    out.add("filled-texture", {
      val fillPixMap = new Pixmap(1, 1, Pixmap.Format.RGBA8888)
      fillPixMap.setColor(Color.WHITE)
      fillPixMap.fill()
      StaticImage.fromPixMap(fillPixMap)
    })
    out
  }

  def apply(app: App): Unit = app.executeOnRenderThread {
    val regions = createTextureRegionLoader(app)
    loadFonts(app, regions)
    loadImages(app, regions)
    loadParticleEffects(app, regions)
    loadBackground(app, regions)
    loadGui(app, regions)
  }

  private def loadFonts(app: App, regions: Loader[TextureRegion]): Unit = {
    app.addResource("font:monospace-default", Font.fromTtfFile("pt-mono/PTM55FT.ttf"))
    app.addResource("font:monospace-default-masked", app.resource[Font]("font:monospace-default").createMaskedInstance(maskChar = Font.DEFAULT_MASK_CHAR, deleteSource = false))
  }

  private def loadImages(app: App, regions: Loader[TextureRegion]): Unit = {
    app.addResource("animation:capguy-walk", Animation(regions("animations/capguy-walk.png"), nx = 8, ny = 1, dt = 0.1, mode = PlayMode.LOOP))
    app.addResource("animation:capguy-walk:instance-0", app.resource[Animation]("animation:capguy-walk").newInstance(t0 = app.localAppTime))
    app.addResource("image:test-image", StaticImage(regions("images/test-image.png")))
  }

  private def loadParticleEffects(app: App, regions: Loader[TextureRegion]): Unit = {
    app.addResource("particle-effect:test-effect:instance-0", Particles.standard("particle-effects/test-effect.party", "particle-effects/").scaleEffect(0.5f))
    def effect0 = app.resource[ParticleSource]("particle-effect:test-effect:instance-0")
    app.addResource("particle-effect:test-effect:instance-1", effect0.copy.scaleEffect(0.5f).flipY().setTint(Color.TEAL))
    app.addResource("particle-effect:test-effect:instance-2", effect0.copy.scaleEffect(0.25f))
    app.addResource("camera-position", Vec2(x = 320.0f, y = 240.0f))
    app.addResource("cool-cursor", createCursor("cursors/c2.png"))
  }

  private def loadBackground(app: App, regions: Loader[TextureRegion]): Unit = {
    app.addResource("bg-image", StaticImage(regions("backgrounds/bgtest2.jpg")))
    app.addResource("background-0",
      MultiLayer[Image]() {
        _.layer(translationScale = 0.5f, camZero = Vec2(320.0f, 240.0f)) {
          _.piece(
            bounds = Box2(ll = Vec2(0.0f, 0.0f), size = Vec2(640.0f, 480.0f)),
            image = app.resource[Image]("bg-image")
          )
        }.layer(translationScale = 0.75f, camZero = Vec2(320.0f, 240.0f)) {
          _.piece(
            bounds = Box2(ll = Vec2(120.0f, 200.0f), size = Vec2(40.0f, 80.0f)),
            image = app.resource[Image]("bg-image")
          ).piece(
            bounds = Box2(ll = Vec2(240.0f, 200.0f), size = Vec2(40.0f, 80.0f)),
            image = app.resource[Image]("bg-image")
          ).piece(
            bounds = Box2(ll = Vec2(360.0f, 200.0f), size = Vec2(40.0f, 80.0f)),
            image = app.resource[Image]("bg-image")
          )
        }
      }
    )
  }

  private def loadGui(app: App, regions: Loader.InMemory[TextureRegion]): Unit = {
    app.addResource("gui:main-menu", Gui(debug = true))

    val mainMenu = app.resource[Gui]("gui:main-menu")

    mainMenu
      .addStyle("fill", classOf[TextureRegion], regions("filled-texture"))
      .addStyle("default-font", classOf[BitmapFont], app.resource[Font]("font:monospace-default").bitmapFont)
      .addStyle("masked-font", classOf[BitmapFont], app.resource[Font]("font:monospace-default-masked").bitmapFont)
      .addStyle("default", classOf[TextButtonStyle], new TextButtonStyle {
        up = mainMenu.newDrawableFromStyle("fill", Color.DARK_GRAY)
        down = mainMenu.newDrawableFromStyle("fill", Color.DARK_GRAY)
        checked = mainMenu.newDrawableFromStyle("fill", Color.BLUE)
        over = mainMenu.newDrawableFromStyle("fill", Color.LIGHT_GRAY)
        font = mainMenu.getStyle[BitmapFont]("default-font")
      })

    mainMenu.row { r =>
      r.cell().height(25)
    }.row { r =>
      r.cell(new TextButton("Click me!", mainMenu.skin)).expandX().left()
      r.cell().width(100)
    }.row { r =>
      r.cellImg("fill", Color.TEAL).size(64)
      r.cellImg("fill", Color.TEAL).size(64)
    }.row { r =>
      r.cell().expandY()
    }
  }

  /*
  private def loadTextureAtlases(app: App): Unit = {
    val texturePackSettings = readTexturePackSettings("test-atlast-cfg.json")
    val inputFolder = new File(getClass.getClassLoader.getResource("test-atlast-cfg.json").getFile).getAbsoluteFile.getParent
    val outputFolder = LoadFile("target/").get.file().getAbsolutePath
    packFilesIntoTextureAtlas(texturePackSettings, inputDir = inputFolder, outputDir = outputFolder, "test-atlast.atlas")
    println(s"$inputFolder -> $outputFolder")
    app.addResource("texture-atlas", new TextureAtlas(s"$outputFolder/test-atlast.atlas", outputFolder))
  }
  */
}