package Mini_Bank

import Mini_Bank.BankSys._
import akka.actor.{Actor, ActorLogging, Props}

import scala.collection.mutable.{ListBuffer, Map}
import scala.io.StdIn.{readInt, readLine}

object BankSys{
  def props():Props=Props(new BankSys())
  case class  CreateAccount(name:String, dob:String, sex:String, contactNo:String, initialBalance:Int)
  case class GetDetails(customerAcNum:Int)
  case class Deposit(customerAcNum:Int,amountDeposit:Int)
  case class WithDrawAmount(customerAcNum:Int,amount:Int)
  case class AccountCreated(accountNum:Int)
  case class WithDrawFailed()
  case class WithDrawSuccess(customerAcNum:Int,amount:Int)
  case class AccountBalance(accountNum:Int)
  case class InvalidAccountNum(customerAcNum:Int)
  case class DisplayDetails(customerAcNum:Int)
  case class MoreThanLimit(withdrawLimit:Int)

}

class BankSys() extends Actor with ActorLogging{

  var accountList = ListBuffer(1000)
  var accountDairy=Map[Int,ListBuffer[String]]()
  val withdrawLimit=2000


  override def receive: Receive = {

    case CreateAccount(name, dob, sex, contactNo, initialBalance) =>

      var accountNum=accountList.last+1
      accountList+=accountNum
      accountDairy(accountNum)=ListBuffer(name,dob,sex,contactNo,initialBalance.toString)
      sender()! AccountCreated(accountNum)






      //val createAccount = context.actorOf(CreateAccount.props(accountDairy,accountList,name, dob, sex, contactNo, initailBalance), "CreateAccount")
      //createAccount ! "."

    case  GetDetails(customerAcNum) if ! accountDairy.contains(customerAcNum)=>
      sender() ! InvalidAccountNum(customerAcNum)

    case  GetDetails(customerAcNum)=>
      self! DisplayDetails(customerAcNum)


      //val detailOfAc=context.actorOf(PrintDetailsOfAccount.props(customerAcNum,accountDairy),"PrintDetails")
      //detailOfAc !"."

    case Deposit(customerAcNum,amountDeposit)=>

      var totalBalance=accountDairy(customerAcNum)(4).toInt + amountDeposit
      accountDairy(customerAcNum)(4)=totalBalance.toString
      println("Updated balance")

      //val deposite=context.actorOf(DepositAmount.props(customerAcNum,accountDairy,amountDeposit),"DeopsiteIntoAC")
      //deposite ! "."

    case WithDrawAmount(customerAcNum,amount)=>


      var balance=accountDairy(customerAcNum)(4).toInt
      if (balance<amount) {
      sender()! WithDrawFailed()
      }
      else if (balance>withdrawLimit)
     sender() ! MoreThanLimit(withdrawLimit)
      else
      {
        accountDairy(customerAcNum)(4)=(balance-amount).toString
        sender() ! WithDrawSuccess(customerAcNum,amount)
       // println("Amount Withdraw")
      }

    case AccountBalance(accountNum)=>
          print(accountDairy(accountNum)(4))
    case DisplayDetails(customerAcNum)=>
      println("Details of AC:"+customerAcNum)



      println("A/c Name :"+accountDairy(customerAcNum)(0)+" || "+
        "DOB :"+accountDairy(customerAcNum)(1)+" || "+"Sex  :"
        +accountDairy(customerAcNum)(2)+" || "+"Contact Number :"
        +accountDairy(customerAcNum)(3)+" || "+"Total Amount :"+accountDairy(customerAcNum)(4))


      //val credit=context.actorOf(WithDraw.props(customerAcNum,amount,accountDairy),"credit")
        //credit ! "Do it"


  }

}
