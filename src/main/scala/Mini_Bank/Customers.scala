package Mini_Bank

import Mini_Bank.BankSys._
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object Customers{
  def props(bank:ActorRef):Props=Props(new Customers(bank))
  case class AccountCreated(accountNum:Int)
  case class WithDrawFailed()
  case class WithDrawSuccess(customerAcNum:Int,amount:Int)
  case class InvalidAccountNum(customerAcNum:Int)
  case class MoreThanLimit(withdrawLimit:Int)
}

class Customers(bank:ActorRef) extends Actor {
  override def receive: Receive ={
    case CreateAccount(name:String,dob:String,sex:String,contactNo:String,initialBalance:Int)=>
      bank ! CreateAccount(name:String,dob:String,sex:String,contactNo:String,initialBalance:Int)
    case GetDetails(accountNum:Int)=>
        bank ! GetDetails(accountNum)
    case Deposit(accountNum:Int,amountDeposit:Int)=>
        bank ! Deposit(accountNum:Int,amountDeposit:Int)
    case WithDrawAmount(accountNum:Int,amountWithDraw:Int)=>
        bank ! WithDrawAmount(accountNum:Int,amountWithDraw:Int)

    case AccountCreated(accountNum)=>
      println(s"Account Created Successfully with AC number :$accountNum")
    case WithDrawFailed()=>
      println("Amout is insufficient")
    case WithDrawSuccess(customerAcNum:Int,amount:Int)=>
      println(s"$amount is successfully Withdrawl from $customerAcNum")
    case  InvalidAccountNum(customerAcNum)=>
        println(s"Account Number: $customerAcNum not valid ")
    case MoreThanLimit(withdrawLimit)=>
        println(s"Amount you asking for is greater than $withdrawLimit")
    /*
          bank ! CreateAccount("Ankit Sharma","14/09/1999","M","9783737201",0)

          bank ! GetDetails(1001)
          bank ! Deposit(1001,2000)
          bank ! WithDrawAmount(1001,5000)
          bank ! GetDetails(1001)*/



  }
 /* def main(args:Array[String]): Unit =
  {
    val actorSystem=ActorSystem("NewOne")
    val cus1=actorSystem.actorOf(Customers.props(bank),"cus1")


  }*/
}