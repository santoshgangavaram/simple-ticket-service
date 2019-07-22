package demo.codechallenge.ticketservice;

public class SeatsNotAvailableException extends RuntimeException {

  /** */
  private static final long serialVersionUID = 1L;

  public SeatsNotAvailableException() {
    super();
  }
  
  public SeatsNotAvailableException(Throwable cause) {
	  super(cause);
  }

  public SeatsNotAvailableException(String message) {
	  super(message);
  }
  
}
