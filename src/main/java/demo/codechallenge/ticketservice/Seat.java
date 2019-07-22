package demo.codechallenge.ticketservice;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Seat {

  final int row;

  final int column;

  int seatHoldId = -1;

  SeatStatus status;

  Instant expiry;

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public Seat(int row, int column) {
    this.row = row;
    this.column = column;
    this.status = SeatStatus.OPEN;
  }

  public int getSeatHoldId() {
    return seatHoldId;
  }

  synchronized void hold(int seatHoldId) {
    this.seatHoldId = seatHoldId;
    hold(Duration.ofSeconds(5));
  }

  void hold(Duration duration) {
    this.expiry =
        LocalDateTime.now()
            .plusSeconds(duration.getSeconds())
            .atZone(ZoneId.systemDefault())
            .toInstant();
    this.status = SeatStatus.HOLD;
  }

  synchronized boolean available() {

    if (this.status.equals(SeatStatus.OPEN)) {
      return true;
    }

    if (this.status.equals(SeatStatus.RESERVED)) {
      return false;
    }

    if (Instant.now(Clock.systemDefaultZone()).isAfter(expiry)) {
      this.status = SeatStatus.OPEN;
      this.seatHoldId = -1;
      return true;
    }
    return false;
  }

  void reserve() {
    this.status = SeatStatus.RESERVED;
  }
  
  @Override
  public String toString() {
	  return "Seat{"
		        + "row="
		        + this.row
		        + " , column="
		        + this.column
		        + '}';
  }
}
