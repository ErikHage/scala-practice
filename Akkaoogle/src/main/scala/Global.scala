import com.tfr.akkaoogle.infrastructure._
import com.tfr.akkaoogle.models.AkkaoogleSchema

/**
  * Created by Erik on 8/5/2017.
  */
object Global extends com.typesafe.play.mini.Setup(com.tfr.akkaoogle.http.AkkaoogleApp) {

  println("Initializing the Akkaoogle schema")
  AkkaoogleSchema.createSchema()
  AkkaoogleActorServer.run()

}
