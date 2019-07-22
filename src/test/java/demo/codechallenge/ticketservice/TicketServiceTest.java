package demo.codechallenge.ticketservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.Test;

public class TicketServiceTest {

  private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  @Test()
  public void getAvailableSeatsTest() {
    TicketServiceImpl service = new TicketServiceImpl();
    assertEquals(306, service.numSeatsAvailable());
    LOGGER.info("getAvailableSeats tested");
  }

  @Test
  public void singleUserReservationTest() {
    TicketServiceImpl service = new TicketServiceImpl();
    SeatHold seatHeld = service.findAndHoldSeats(10, "test@gmail.com");
    assertEquals(10, seatHeld.getHeldSeats().size());
    String reservationCode = service.reserveSeats(seatHeld.getId(), "test@gmail.com");
    assertNotNull("ReservationCode is: " + reservationCode, reservationCode);
    LOGGER.info("SingleUserReservation tested");
  }

  @Test
  public void singleUserHeldSeatsExpiryTest() throws InterruptedException {
    TicketServiceImpl service = new TicketServiceImpl();
    SeatHold seatHeld = service.findAndHoldSeats(20, "test@gmail.com");    
    assertEquals(20, seatHeld.getHeldSeats().size());
    assertEquals(286, service.numSeatsAvailable());
    TimeUnit.SECONDS.sleep(10);
    assertEquals(306, service.numSeatsAvailable());
    LOGGER.info("SingleUserHeldSeatsExpiry tested");
  }

  @Test
  public void concurrentUserSeatHoldTest() throws InterruptedException, ExecutionException {
    TicketServiceImpl service = new TicketServiceImpl();

    List<CompletableFuture<SeatHold>> seatHoldFutures = new ArrayList<>();

    seatHoldFutures.add(
        CompletableFuture.supplyAsync(() -> service.findAndHoldSeats(5, "test@gmail.com")));
    seatHoldFutures.add(
        CompletableFuture.supplyAsync(() -> service.findAndHoldSeats(5, "test2@gmail.com")));

    CompletableFuture<Void> allFutures =
        CompletableFuture.allOf(seatHoldFutures.get(0), seatHoldFutures.get(1));

    CompletableFuture<List<SeatHold>> allPageContentsFuture =
        allFutures.thenApply(
            v -> {
              return seatHoldFutures
                  .stream()
                  .map(seatHoldFuture -> seatHoldFuture.join())
                  .collect(Collectors.toList());
            });

    List<SeatHold> seatHolds = allPageContentsFuture.get();

    boolean heldSeatsAreSame =
        seatHolds.get(0).getHeldSeats().equals(seatHolds.get(1).getHeldSeats());

    LOGGER.info("ConcurrentUserSeatHold tested");

    assertEquals(false, heldSeatsAreSame);
  }

  @Test(expected = SeatsNotAvailableException.class)
  public void seatsUnAvailabilityTest() {
    TicketServiceImpl service = new TicketServiceImpl();
    LOGGER.info("SeatsUnAvailability about to test");
    service.findAndHoldSeats(40, "test@gmail.com");
  }

  @Test(expected = SeatHoldIdNotFoundException.class)
  public void seatHoldIdUnAvailabilityTest() {
    TicketServiceImpl service = new TicketServiceImpl();
    LOGGER.info("SeatHoldId Unavailability about to test");
    service.reserveSeats(10, "test@gmail.com");
  }
}
