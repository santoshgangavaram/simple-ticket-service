package demo.codechallenge.ticketservice;

public class SeatHoldIdNotFoundException extends RuntimeException {

  /** */
  private static final long serialVersionUID = 1L;

  public SeatHoldIdNotFoundException() {
    super();
  }
  
  public SeatHoldIdNotFoundException(Throwable cause) {
	  super(cause);
  }

  public SeatHoldIdNotFoundException(String message) {
	  super(message);
  }
  
}
