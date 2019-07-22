package demo.codechallenge.ticketservice;

import java.util.Optional;

public enum SeatStatus {
		
	OPEN("O"),
	HOLD("H"),
	RESERVED("R");
	
	final String code;
	
	SeatStatus(String code) {
		this.code = code;
	}
	
	String to() {
		return this.code;
	}
	
	public static SeatStatus from(String marshalled)
    {
        marshalled = Optional.ofNullable(marshalled).map(String::trim).orElse("");
        for (SeatStatus seatStatus : values())
        {
            if (seatStatus.code.equalsIgnoreCase(marshalled))
                return seatStatus;
        }   
        return null;
    }
}
