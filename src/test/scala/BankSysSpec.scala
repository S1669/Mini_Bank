
import Mini_Bank.BankSys.{AccountBalance, AccountCreated, CreateAccount, DisplayDetails, GetDetails, InvalidAccountNum, WithDrawAmount, WithDrawFailed, WithDrawSuccess,MoreThanLimit}
import Mini_Bank.Customers
import Mini_Bank.{BankSys, Customers}
import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestActors, TestKit, TestProbe}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class BankSysSpec()
  extends TestKit(ActorSystem("BankSpec"))
    with ImplicitSender
    with AnyWordSpecLike
    with Matchers
    with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "A BankSys" must {




    "Must Written Msg after created account" in {
      val sender = TestProbe()
      val bank = system.actorOf(Props[BankSys])
      sender.send(bank,CreateAccount("Shubham Soni", "01/08/1999", "M", "6377014020", 10000))
      sender.expectMsg(AccountCreated(1001))
    }

    "Show invalid msg when requested for details of account not exist:" in{
      val sender=TestProbe()
      val bank=system.actorOf(Props[BankSys])
      sender.send(bank,GetDetails(2000))
      sender.expectMsg(InvalidAccountNum(2000))
    }
  /*  "Display Details when account info is correct" in {
      val sender=TestProbe()
      val bank=system.actorOf(Props[BankSys])
      sender.send(bank,CreateAccount("Gangu Bai", "01/08/1990", "F", "6377015020", 10000))
      val customerAcNum=1001
      sender.expectMsg(AccountCreated(customerAcNum))
      sender.send(bank,GetDetails(customerAcNum))
      sender.expectMsg(DisplayDetails(customerAcNum))

    }*/


    "If amount is insufficent and you want to withdraw money" in {
      val sender=TestProbe()
      val bank=system.actorOf(Props[BankSys])
      sender.send(bank,CreateAccount("Gangu Bai2", "01/08/1990", "F", "6377015020", 10000))
      sender.expectMsg(AccountCreated(1001))
      val customerAcNum=1001
      val amount =50000
      sender.send(bank,WithDrawAmount(customerAcNum,amount))
      sender.expectMsg(WithDrawFailed())

    }
    "Amount Withdraw successfully when balance is enough" in{
      val sender=TestProbe()
      val bank=system.actorOf(Props[BankSys])
      sender.send(bank,CreateAccount("Mastani", "07/08/1890", "F", "6877015020", 1000))
      sender.expectMsg(AccountCreated(1001))
      val customerAcNum=1001
      val amount =500
      sender.send(bank,WithDrawAmount(customerAcNum,amount))
      sender.expectMsg(WithDrawSuccess(customerAcNum,amount))

    }
    "Request for withdraw more than Limit" in {
      val sender=TestProbe()
      val bank=system.actorOf(Props[BankSys])
      sender.send(bank,CreateAccount("Padmavati", "07/08/1890", "F", "6877015020", 10000))

      sender.expectMsg(AccountCreated(1001))
      sender.send(bank,WithDrawAmount(1001,3000))
      sender.expectMsg(MoreThanLimit(2000))

    }





  }
}