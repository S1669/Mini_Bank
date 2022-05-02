package Mini_Bank

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.io.StdIn.readInt

object MainA{
  def main(args:Array[String]): Unit =
  {
    import BankSys._
    import  CreateAccount._
    val actorSystem=ActorSystem("NewSystem")


    val Bank=actorSystem.actorOf(BankSys.props,"SBI")
    val c1=actorSystem.actorOf(Customers.props(Bank),"Cus1")

    c1! CreateAccount("Shubham Soni","01/08/2000","M","9521316066",1000)
    val c2=actorSystem.actorOf(Customers.props(Bank),"Cus2")
    c2 ! GetDetails(1001)
    val c3=actorSystem.actorOf(Customers.props(Bank),"Cus3")
    c3 !GetDetails(1002)

    actorSystem.terminate()

  }
}
