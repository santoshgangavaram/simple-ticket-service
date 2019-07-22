package demo.codechallenge.ticketservice;

import java.util.ArrayList;
import java.util.List;

public class SeatHold {

  final String customerEmail;

  final List<Seat> heldSeats = new ArrayList<>();

  final int id;

  public SeatHold(int id, String customerEmail) {
    this.id = id;
    this.customerEmail = customerEmail;
  }

  public int getId() {
    return id;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public List<Seat> getHeldSeats() {
    return heldSeats;
  }

  @Override
  public String toString() {
    return "SeatHold{"
        + "id="
        + this.id
        + " , customerEmail="
        + this.customerEmail
        + ", heldSeats="
        + this.heldSeats
        + '}';
  }
}
