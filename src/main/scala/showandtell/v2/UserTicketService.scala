package showandtell.v2

import cats.effect.IO

case class User(id: String, name: String)
case class Ticket(id: String, text: String)
case class UserTicket(ticketText: String, userName: String)

trait UserService {
  def getUser(userId: String): IO[User]

  def deleteUser(userId: String): IO[Unit]

  def createUser(user: User): IO[Unit]
}

trait TicketService {
  def buyTicket(ticketId: String): IO[Ticket]

  def cancelTicket(ticketId: String): IO[Boolean]
}

trait UserTicketService {
  def buyPersonalizedTicket(userId: String, ticketId: String): IO[UserTicket]
}

trait UserTicketServiceImpl extends UserTicketService {

  protected def getUser(userId: String): IO[User]

  protected def buyTicket(ticketId: String): IO[Ticket]

  final def buyPersonalizedTicket(userId: String, ticketId: String): IO[UserTicket] =
    for {
      user   <- getUser(userId)
      ticket <- buyTicket(ticketId)
    } yield UserTicket(ticket.text, user.name)
    
}

object UserTicketService {
  def apply(userService: UserService, ticketService: TicketService): UserTicketService =
    new UserTicketServiceImpl {
      def buyTicket(ticketId: String): IO[Ticket] =
        ticketService.buyTicket(ticketId)

      def getUser(userId: String): IO[User] =
        userService.getUser(userId)
    }
}
