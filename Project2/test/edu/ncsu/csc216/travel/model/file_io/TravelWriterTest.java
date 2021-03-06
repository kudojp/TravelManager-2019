/**
 * 
 */
package edu.ncsu.csc216.travel.model.file_io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.travel.model.office.DuplicateClientException;
import edu.ncsu.csc216.travel.model.office.DuplicateTourException;
import edu.ncsu.csc216.travel.model.office.TourCoordinator;
import edu.ncsu.csc216.travel.model.vacation.CapacityException;
import edu.ncsu.csc216.travel.model.vacation.Reservation;

/**
 * Test class for TravelWriter class.
 * @author dkudo
 *
 */
public class TravelWriterTest {
	
	
	/**
	 * Sets up the Reservation class
	 * Resets confirmation code.
	 */
	@Before
    public void setUp() {
      Reservation.resetCodeGenerator();

    }
	


	/**
	 * Test method for {@link edu.ncsu.csc216.travel.model.file_io.TravelWriter#writeTravelData(java.lang.String)}.
	 */
	@Test
	public void testWriteTravelData() {
		
		// check the filename validity
		try {
			TravelWriter.writeTravelData("test-files/file.txt");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "File not saved.");
		}
		// check the filename validity
		try {
			TravelWriter.writeTravelData("test-files/.md");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "File not saved.");
		}
		// check the filename validity
		try {
			TravelWriter.writeTravelData("  .md");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "File not saved.");
		}

		
		
		
		
		TourCoordinator tc = TourCoordinator.getInstance();
		tc.flushLists();
		
		try {
			tc.addNewClient("user1", "contact1");
			tc.addNewClient("user2", "contact2");
			tc.addNewClient("user3", "contact3");
		} catch (DuplicateClientException e) {
			fail();
		}
		
		
		try {
			tc.addNewTour("Education", "et1", LocalDate.of(2019, 1, 1), 1, 200, 100);
			tc.addNewTour("Education", "et2", LocalDate.of(2019, 1, 1), 2, 200, 100);
			tc.addNewTour("Land Tour", "lt", LocalDate.of(2019, 1, 1), 3, 200, 100);
			tc.addNewTour("River Cruise", "rc1", LocalDate.of(2019, 1, 1), 4, 200, 100);
			tc.addNewTour("River Cruise", "rc2", LocalDate.of(2019, 1, 1), 5, 200, 100);
		} catch (DuplicateTourException e) {
			fail();
		}
		
		
		try {
			// reservation 000000 client 1 for et1 (60)
			tc.addNewReservation(0, 0, 60);
			// another reservation 000001 client 2 for et1 (60)
			tc.addNewReservation(1, 0, 60);
			
			// reservation 000002 client 2 for et2 (60)
			tc.addNewReservation(1, 1, 60);
			// reservation 000003 client 2 for lt (60)
			tc.addNewReservation(1, 2, 60);
			// reservation 000004 client 3 for rc1 (60)
			tc.addNewReservation(2, 3, 60);
			
		} catch (CapacityException e){
			fail();
		}
		
		TravelWriter.writeTravelData("test-files/actualOutputFile.md");
		assertTrue(compareFiles("test-files/expectedOutputFile.md", "test-files/actualOutputFile.md"));
		
	}
	
	
	/**
	 * Helper static method which compares 2 files.
	 * @param filename1 filename 
	 * @param filename2 filename of the other file
	 * @return True if the contents of 2 files are identical.
	 */
	public static boolean compareFiles(String filename1, String filename2) {

		try {
			Scanner f1Scanner = new Scanner(new File(filename1));
			Scanner f2Scanner = new Scanner(new File(filename2));
			
			while(f1Scanner.hasNext() && f2Scanner.hasNext()) {
				
				// if the next lines are not identical,,
				if (!f1Scanner.nextLine().equals(f2Scanner.nextLine())){
					f1Scanner.close();
					f2Scanner.close();
					return false;
				}
			}
			
			
			// if only file1 has more lines,,,
			if (f1Scanner.hasNext()) {
				f1Scanner.close();
				f2Scanner.close();
				return false;
			}
			
			// if only file2 has more lines,,,
			if (f2Scanner.hasNext()) {
				f1Scanner.close();
				f2Scanner.close();
				return false;
			}
			
			f1Scanner.close();
			f2Scanner.close();
			
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Error when reading a file.");
		}
		
		return true;
	}


}
