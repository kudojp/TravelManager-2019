/**
 * 
 */
package edu.ncsu.csc216.travel.model.vacation;

import java.time.LocalDate;

/**
 * Class of Educational Tour
 * @author dkudo
 *
 */
public class EducationalTrip extends Tour {
	
	/** prefix which represents EducationalTrip */
	private static String prefix = "ED-";

	/**
	 * Constructs EducationalTrip object
	 * @param name : name of the EducationalTrip
	 * @param start : start time of the EducationalTrip
	 * @param duration : duration of the Educational Trip
	 * @param basePrice : basePrice of the EducationalTrip
	 * @param capacity : capacity of the EducationalTrip
	 * @throws IllegalArgumentException : if any parameters are not valid (including the parameter corresponding to capacity)
	 */
	public EducationalTrip(String name, LocalDate start, int duration, int basePrice, int capacity) {
		super(name, start, duration, basePrice, capacity);	
	}
	
	

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.travel.model.vacation.Tour#getName()
	 */
	@Override
	public String getName() {
		return prefix + super.getName();
	}

	
	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.travel.model.vacation.Tour#costFor(int)
	 */
	@Override
	public double costFor(int i) {
		// For educational tours, the cost is base price X party size.
		return super.getBasePrice() * i;
	}

}
