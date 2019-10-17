package showandtell.v1

import org.scalatest._
import cats.effect.IO

class UserTicketServiceSpec extends FunSuite {

  val user = User("1", "Joe")
  val ticket = Ticket("9", "Yearly Ticket")

  val userService = new UserService {
    def createUser(user: User): IO[Unit]     = ???
    def deleteUser(userId: String): IO[Unit] = ???
    def getUser(userId: String): IO[User]    = IO.pure(user)
  }

  val ticketService = new TicketService {
    def buyTicket(ticketId: String): IO[Ticket] = IO.pure(ticket)
    def cancelTicket(ticketId: String): IO[Boolean] = ???
  }

  val userTicketService = UserTicketService(userService, ticketService)

  test("We can buy a ticket for a user") {
    userTicketService.buyPersonalizedTicket(user.id, ticket.id).map { userTicket => 
      assert(userTicket == UserTicket(ticket.text, user.name))
    }
  }

}
