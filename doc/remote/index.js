Index.PACKAGES = {"cml" : [{"name" : "cml.RemoteActor", "shortDescription" : "This class implements remote actor utils for battle managements", "object" : "cml\/RemoteActor$.html", "members_class" : [{"label" : "Second", "tail" : ": Int", "member" : "cml.RemoteActor.Second", "link" : "cml\/RemoteActor.html#Second:Int", "kind" : "val"}, {"label" : "First", "tail" : ": Int", "member" : "cml.RemoteActor.First", "link" : "cml\/RemoteActor.html#First:Int", "kind" : "val"}, {"label" : "receive", "tail" : "(): Receive", "member" : "cml.RemoteActor.receive", "link" : "cml\/RemoteActor.html#receive:RemoteActor.this.Receive", "kind" : "def"}, {"label" : "isFirst", "tail" : ": Boolean", "member" : "cml.RemoteActor.isFirst", "link" : "cml\/RemoteActor.html#isFirst:Boolean", "kind" : "var"}, {"label" : "actorInList", "tail" : ": ListBuffer[ActorRef]", "member" : "cml.RemoteActor.actorInList", "link" : "cml\/RemoteActor.html#actorInList:scala.collection.mutable.ListBuffer[akka.actor.ActorRef]", "kind" : "var"}, {"label" : "DefaultMessage", "tail" : ": String", "member" : "cml.RemoteActor.DefaultMessage", "link" : "cml\/RemoteActor.html#DefaultMessage:String", "kind" : "val"}, {"member" : "cml.RemoteActor#<init>", "error" : "unsupported entity"}, {"label" : "log", "tail" : "(): LoggingAdapter", "member" : "akka.actor.ActorLogging.log", "link" : "cml\/RemoteActor.html#log:akka.event.LoggingAdapter", "kind" : "def"}, {"label" : "unhandled", "tail" : "(message: Any): Unit", "member" : "akka.actor.Actor.unhandled", "link" : "cml\/RemoteActor.html#unhandled(message:Any):Unit", "kind" : "def"}, {"label" : "postRestart", "tail" : "(reason: Throwable): Unit", "member" : "akka.actor.Actor.postRestart", "link" : "cml\/RemoteActor.html#postRestart(reason:Throwable):Unit", "kind" : "def"}, {"label" : "preRestart", "tail" : "(reason: Throwable, message: Option[Any]): Unit", "member" : "akka.actor.Actor.preRestart", "link" : "cml\/RemoteActor.html#preRestart(reason:Throwable,message:Option[Any]):Unit", "kind" : "def"}, {"label" : "postStop", "tail" : "(): Unit", "member" : "akka.actor.Actor.postStop", "link" : "cml\/RemoteActor.html#postStop():Unit", "kind" : "def"}, {"label" : "preStart", "tail" : "(): Unit", "member" : "akka.actor.Actor.preStart", "link" : "cml\/RemoteActor.html#preStart():Unit", "kind" : "def"}, {"label" : "supervisorStrategy", "tail" : "(): SupervisorStrategy", "member" : "akka.actor.Actor.supervisorStrategy", "link" : "cml\/RemoteActor.html#supervisorStrategy:akka.actor.SupervisorStrategy", "kind" : "def"}, {"label" : "aroundPostRestart", "tail" : "(reason: Throwable): Unit", "member" : "akka.actor.Actor.aroundPostRestart", "link" : "cml\/RemoteActor.html#aroundPostRestart(reason:Throwable):Unit", "kind" : "def"}, {"label" : "aroundPreRestart", "tail" : "(reason: Throwable, message: Option[Any]): Unit", "member" : "akka.actor.Actor.aroundPreRestart", "link" : "cml\/RemoteActor.html#aroundPreRestart(reason:Throwable,message:Option[Any]):Unit", "kind" : "def"}, {"label" : "aroundPostStop", "tail" : "(): Unit", "member" : "akka.actor.Actor.aroundPostStop", "link" : "cml\/RemoteActor.html#aroundPostStop():Unit", "kind" : "def"}, {"label" : "aroundPreStart", "tail" : "(): Unit", "member" : "akka.actor.Actor.aroundPreStart", "link" : "cml\/RemoteActor.html#aroundPreStart():Unit", "kind" : "def"}, {"label" : "aroundReceive", "tail" : "(receive: akka.actor.Actor.Receive, msg: Any): Unit", "member" : "akka.actor.Actor.aroundReceive", "link" : "cml\/RemoteActor.html#aroundReceive(receive:akka.actor.Actor.Receive,msg:Any):Unit", "kind" : "def"}, {"label" : "sender", "tail" : "(): ActorRef", "member" : "akka.actor.Actor.sender", "link" : "cml\/RemoteActor.html#sender():akka.actor.ActorRef", "kind" : "final def"}, {"label" : "self", "tail" : ": ActorRef", "member" : "akka.actor.Actor.self", "link" : "cml\/RemoteActor.html#self:akka.actor.ActorRef", "kind" : "implicit final val"}, {"label" : "context", "tail" : ": ActorContext", "member" : "akka.actor.Actor.context", "link" : "cml\/RemoteActor.html#context:akka.actor.ActorContext", "kind" : "implicit val"}, {"label" : "Receive", "tail" : "", "member" : "akka.actor.Actor.Receive", "link" : "cml\/RemoteActor.html#Receive=akka.actor.Actor.Receive", "kind" : "type"}, {"label" : "synchronized", "tail" : "(arg0: ⇒ T0): T0", "member" : "scala.AnyRef.synchronized", "link" : "cml\/RemoteActor.html#synchronized[T0](x$1:=>T0):T0", "kind" : "final def"}, {"label" : "##", "tail" : "(): Int", "member" : "scala.AnyRef.##", "link" : "cml\/RemoteActor.html###():Int", "kind" : "final def"}, {"label" : "!=", "tail" : "(arg0: Any): Boolean", "member" : "scala.AnyRef.!=", "link" : "cml\/RemoteActor.html#!=(x$1:Any):Boolean", "kind" : "final def"}, {"label" : "==", "tail" : "(arg0: Any): Boolean", "member" : "scala.AnyRef.==", "link" : "cml\/RemoteActor.html#==(x$1:Any):Boolean", "kind" : "final def"}, {"label" : "ne", "tail" : "(arg0: AnyRef): Boolean", "member" : "scala.AnyRef.ne", "link" : "cml\/RemoteActor.html#ne(x$1:AnyRef):Boolean", "kind" : "final def"}, {"label" : "eq", "tail" : "(arg0: AnyRef): Boolean", "member" : "scala.AnyRef.eq", "link" : "cml\/RemoteActor.html#eq(x$1:AnyRef):Boolean", "kind" : "final def"}, {"label" : "finalize", "tail" : "(): Unit", "member" : "scala.AnyRef.finalize", "link" : "cml\/RemoteActor.html#finalize():Unit", "kind" : "def"}, {"label" : "wait", "tail" : "(): Unit", "member" : "scala.AnyRef.wait", "link" : "cml\/RemoteActor.html#wait():Unit", "kind" : "final def"}, {"label" : "wait", "tail" : "(arg0: Long, arg1: Int): Unit", "member" : "scala.AnyRef.wait", "link" : "cml\/RemoteActor.html#wait(x$1:Long,x$2:Int):Unit", "kind" : "final def"}, {"label" : "wait", "tail" : "(arg0: Long): Unit", "member" : "scala.AnyRef.wait", "link" : "cml\/RemoteActor.html#wait(x$1:Long):Unit", "kind" : "final def"}, {"label" : "notifyAll", "tail" : "(): Unit", "member" : "scala.AnyRef.notifyAll", "link" : "cml\/RemoteActor.html#notifyAll():Unit", "kind" : "final def"}, {"label" : "notify", "tail" : "(): Unit", "member" : "scala.AnyRef.notify", "link" : "cml\/RemoteActor.html#notify():Unit", "kind" : "final def"}, {"label" : "toString", "tail" : "(): String", "member" : "scala.AnyRef.toString", "link" : "cml\/RemoteActor.html#toString():String", "kind" : "def"}, {"label" : "clone", "tail" : "(): AnyRef", "member" : "scala.AnyRef.clone", "link" : "cml\/RemoteActor.html#clone():Object", "kind" : "def"}, {"label" : "equals", "tail" : "(arg0: Any): Boolean", "member" : "scala.AnyRef.equals", "link" : "cml\/RemoteActor.html#equals(x$1:Any):Boolean", "kind" : "def"}, {"label" : "hashCode", "tail" : "(): Int", "member" : "scala.AnyRef.hashCode", "link" : "cml\/RemoteActor.html#hashCode():Int", "kind" : "def"}, {"label" : "getClass", "tail" : "(): Class[_]", "member" : "scala.AnyRef.getClass", "link" : "cml\/RemoteActor.html#getClass():Class[_]", "kind" : "final def"}, {"label" : "asInstanceOf", "tail" : "(): T0", "member" : "scala.Any.asInstanceOf", "link" : "cml\/RemoteActor.html#asInstanceOf[T0]:T0", "kind" : "final def"}, {"label" : "isInstanceOf", "tail" : "(): Boolean", "member" : "scala.Any.isInstanceOf", "link" : "cml\/RemoteActor.html#isInstanceOf[T0]:Boolean", "kind" : "final def"}], "members_object" : [{"label" : "main", "tail" : "(args: Array[String]): Unit", "member" : "cml.RemoteActor.main", "link" : "cml\/RemoteActor$.html#main(args:Array[String]):Unit", "kind" : "def"}, {"label" : "synchronized", "tail" : "(arg0: ⇒ T0): T0", "member" : "scala.AnyRef.synchronized", "link" : "cml\/RemoteActor$.html#synchronized[T0](x$1:=>T0):T0", "kind" : "final def"}, {"label" : "##", "tail" : "(): Int", "member" : "scala.AnyRef.##", "link" : "cml\/RemoteActor$.html###():Int", "kind" : "final def"}, {"label" : "!=", "tail" : "(arg0: Any): Boolean", "member" : "scala.AnyRef.!=", "link" : "cml\/RemoteActor$.html#!=(x$1:Any):Boolean", "kind" : "final def"}, {"label" : "==", "tail" : "(arg0: Any): Boolean", "member" : "scala.AnyRef.==", "link" : "cml\/RemoteActor$.html#==(x$1:Any):Boolean", "kind" : "final def"}, {"label" : "ne", "tail" : "(arg0: AnyRef): Boolean", "member" : "scala.AnyRef.ne", "link" : "cml\/RemoteActor$.html#ne(x$1:AnyRef):Boolean", "kind" : "final def"}, {"label" : "eq", "tail" : "(arg0: AnyRef): Boolean", "member" : "scala.AnyRef.eq", "link" : "cml\/RemoteActor$.html#eq(x$1:AnyRef):Boolean", "kind" : "final def"}, {"label" : "finalize", "tail" : "(): Unit", "member" : "scala.AnyRef.finalize", "link" : "cml\/RemoteActor$.html#finalize():Unit", "kind" : "def"}, {"label" : "wait", "tail" : "(): Unit", "member" : "scala.AnyRef.wait", "link" : "cml\/RemoteActor$.html#wait():Unit", "kind" : "final def"}, {"label" : "wait", "tail" : "(arg0: Long, arg1: Int): Unit", "member" : "scala.AnyRef.wait", "link" : "cml\/RemoteActor$.html#wait(x$1:Long,x$2:Int):Unit", "kind" : "final def"}, {"label" : "wait", "tail" : "(arg0: Long): Unit", "member" : "scala.AnyRef.wait", "link" : "cml\/RemoteActor$.html#wait(x$1:Long):Unit", "kind" : "final def"}, {"label" : "notifyAll", "tail" : "(): Unit", "member" : "scala.AnyRef.notifyAll", "link" : "cml\/RemoteActor$.html#notifyAll():Unit", "kind" : "final def"}, {"label" : "notify", "tail" : "(): Unit", "member" : "scala.AnyRef.notify", "link" : "cml\/RemoteActor$.html#notify():Unit", "kind" : "final def"}, {"label" : "toString", "tail" : "(): String", "member" : "scala.AnyRef.toString", "link" : "cml\/RemoteActor$.html#toString():String", "kind" : "def"}, {"label" : "clone", "tail" : "(): AnyRef", "member" : "scala.AnyRef.clone", "link" : "cml\/RemoteActor$.html#clone():Object", "kind" : "def"}, {"label" : "equals", "tail" : "(arg0: Any): Boolean", "member" : "scala.AnyRef.equals", "link" : "cml\/RemoteActor$.html#equals(x$1:Any):Boolean", "kind" : "def"}, {"label" : "hashCode", "tail" : "(): Int", "member" : "scala.AnyRef.hashCode", "link" : "cml\/RemoteActor$.html#hashCode():Int", "kind" : "def"}, {"label" : "getClass", "tail" : "(): Class[_]", "member" : "scala.AnyRef.getClass", "link" : "cml\/RemoteActor$.html#getClass():Class[_]", "kind" : "final def"}, {"label" : "asInstanceOf", "tail" : "(): T0", "member" : "scala.Any.asInstanceOf", "link" : "cml\/RemoteActor$.html#asInstanceOf[T0]:T0", "kind" : "final def"}, {"label" : "isInstanceOf", "tail" : "(): Boolean", "member" : "scala.Any.isInstanceOf", "link" : "cml\/RemoteActor$.html#isInstanceOf[T0]:Boolean", "kind" : "final def"}], "class" : "cml\/RemoteActor.html", "kind" : "class"}]};