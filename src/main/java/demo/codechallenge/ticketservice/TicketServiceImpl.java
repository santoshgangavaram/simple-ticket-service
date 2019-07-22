package demo.codechallenge.ticketservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class TicketServiceImpl implements TicketService {

  private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  List<List<Seat>> seats = new ArrayList<>();

  Random seatHoldIdRandom = new Random();

  Random reservationCodeRandom = new Random();

  public TicketServiceImpl() {
    for (int i = 0; i < 9; i++) {
      List<Seat> rowSeats = new ArrayList<>();
      for (int j = 0; j < 34; j++) {
        Seat seat = new Seat(i, j);
        rowSeats.add(seat);
      }
      seats.add(rowSeats);
    }
  }

  @Override
  public int numSeatsAvailable() {

    int total = 0;

    for (List<Seat> rowSeats : seats) {
      total += rowSeats.stream().filter(Seat::available).count();
    }

    return total;
  }

  @Override
  public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {

    for (List<Seat> rowSeats : seats) {
      for (int i = 0; i < rowSeats.size() - numSeats; i++) {
        List<Seat> contiguousSeats = new ArrayList<>();
        for (int j = i; j < numSeats; j++) {
          if (rowSeats.get(j).available()) {
            contiguousSeats.add(rowSeats.get(j));
          }
        }
        if (contiguousSeats.size() == numSeats) {
          return seatHold(contiguousSeats, customerEmail);
        }
      }
    }

    LOGGER.severe("Seats Not Found for customer: "+customerEmail+ " for seat count: "+numSeats);
    
    throw new SeatsNotAvailableException("Seats Not Found");
  }

  private SeatHold seatHold(List<Seat> contiguousSeats, String customerEmail) {
    SeatHold seatHold = new SeatHold(seatHoldIdRandom.nextInt(), customerEmail);
    seatHold.getHeldSeats().addAll(contiguousSeats);
    for (Seat seat : seatHold.getHeldSeats()) {
      seat.hold(seatHold.getId());
    }
    return seatHold;
  }

  @Override
  public String reserveSeats(int seatHoldId, String customerEmail) {

    boolean reservationCodeGenerated = false;
    String reservationCode = null;
    for (List<Seat> rowSeats : seats) {
      Stream<Seat> heldSeats = rowSeats.stream().filter(seat -> seat.getSeatHoldId() == seatHoldId);
      for (Seat seat : enhancedFor(heldSeats)) {
        seat.reserve();
        if (!reservationCodeGenerated) {
          reservationCode = "ReservationCode" + reservationCodeRandom.nextInt();
          reservationCodeGenerated = true;
        }
      }
    }

    if (reservationCodeGenerated) {
      return reservationCode;
    }

    LOGGER.severe("SeatHoldId "+seatHoldId+ " is not found for customer: "+customerEmail);
    
    throw new SeatHoldIdNotFoundException("Seats Not Found");
  }

  static <T> Iterable<T> enhancedFor(Stream<T> stream) {
    return stream::iterator;
  }
}
