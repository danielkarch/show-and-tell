package showandtell.v2

import org.scalatest._
import cats.effect.IO

class UserTicketServiceSpec extends FunSuite {

  val user   = User("1", "Joe")
  val ticket = Ticket("9", "Yearly Ticket")

  val userTicketService = new UserTicketServiceImpl {
    def buyTicket(ticketId: String): IO[Ticket] = IO.pure(ticket)
    def getUser(userId: String): IO[User]       = IO.pure(user)
  }

  test("We can buy a ticket for a user") {
    userTicketService.buyPersonalizedTicket(user.id, ticket.id).map { userTicket =>
      assert(userTicket == UserTicket(ticket.text, user.name))
    }
  }

}
