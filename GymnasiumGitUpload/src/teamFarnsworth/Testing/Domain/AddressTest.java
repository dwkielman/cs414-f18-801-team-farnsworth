package teamFarnsworth.Testing.Domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teamFarnsworth.Domain.Address;

public class AddressTest {

	private Address a1;
	private Address a2;
	private Address a1Copy;
	private Address nullAddress;
	private String st1= "1234 Douglas Blvd";
	private String c1= "Sacramento";
	private String sa1= "CA";
	private int z1=  95746;
	private String st2 = "9876 Barton Rd";
	private String c2 =  "Granite Bay";
	private String sa2 = "CA";
	private int z2 = 95650;
					
	@BeforeEach
	void setUp() throws Exception {
		a1 = new Address(st1, c1, sa1, z1);
		a2 = new Address(st2, c2, sa2, z2);
		a1Copy = new Address(st1, c1, sa1, z1);
	}

	@Test
	void testAddressEquals() {
		assertTrue(a1.equals(a1Copy));
	}
	
	@Test
	void testAddressNotEquals() {
		assertFalse(a1.equals(a2));
	}
	
	@Test
	void testAddressNull() {
		assertTrue(nullAddress == null);
	}

}
