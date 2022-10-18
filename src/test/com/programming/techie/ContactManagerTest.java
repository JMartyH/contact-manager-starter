package com.programming.techie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {
	
	ContactManager contactManager;
	
	@BeforeAll
	public void setupAll() {
		System.out.print("Should Print Before All Tests\n");
	}
	
	
	// Since we have already put the object instantiation here,
	// We do not need to instantiate per method
	// That is why it is commented out on below methods
	@BeforeEach
	public void setup() {
		contactManager = new ContactManager();
	}
	

	// @Disabled can be used to skip a test
	// @Disabled
	@Test
	public void shouldCreateContact() {
//		ContactManager contactManager = new ContactManager();
		contactManager.addContact("John", "Hernandez", "0123456789");
		Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
		Assertions.assertEquals( 1, contactManager.getAllContacts().size());
		Assertions.assertTrue(contactManager.getAllContacts().stream()
				.filter(contact -> contact.getFirstName().equals("John") &&
						contact.getLastName().equals("Hernandez") &&
						contact.getPhoneNumber().equals("0123456789"))
				.findAny()
				.isPresent());
	}
	
	@Test
	@DisplayName("Should Not Create Contact When First Name is Null")
	public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
//		ContactManager contactManager = new ContactManager();
		Assertions.assertThrows(RuntimeException.class, () ->{
			contactManager.addContact(null, "Hernandez", "0123456789");
		});
	}
	
	@Test
	@DisplayName("Should Not Create Contact When Last Name is Null")
	public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
//		ContactManager contactManager = new ContactManager();
		Assertions.assertThrows(RuntimeException.class, () ->{
			contactManager.addContact("John", null, "0123456789");
		});
	}
	
	@Test
	@DisplayName("Should Not Create Contact When Phone Number is Null")
	public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
		ContactManager contactManager = new ContactManager();
		Assertions.assertThrows(RuntimeException.class, () ->{
			contactManager.addContact("John", "Hernandez", null);
		});
	}
	
	
	@AfterEach
	public void tearDown() {
		System.out.print("Should Execute After Each Test\n");
	}
	
	@AfterAll
	public void tearDownAll() {
		System.out.print("Should Be Executed At The End Of The Test\n");
	}
	
	
	// Below is an example of conditional execution
	// The test will be skipped if we are on a Windows OS
	@Test
	@DisplayName("Should not create contact on Windows OS")
	@DisabledOnOs(value= OS.WINDOWS, disabledReason = "Disabled on Windows OS")
	public void shouldNotCreateContactOnWindows() {
		contactManager.addContact("John", "Hernandez", "0123456789");
		Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
		Assertions.assertEquals( 1, contactManager.getAllContacts().size());
		Assertions.assertTrue(contactManager.getAllContacts().stream()
				.filter(contact -> contact.getFirstName().equals("John") &&
						contact.getLastName().equals("Hernandez") &&
						contact.getPhoneNumber().equals("0123456789"))
				.findAny()
				.isPresent());
	}
	
	@Test
	@DisplayName("Test Contact Creation on Developer Machine")
	public void shouldTestContactCreationOnDEV() {
	    Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
	    contactManager.addContact("John", "Doe", "0123456789");
	    assertFalse(contactManager.getAllContacts().isEmpty());
	    assertEquals(1, contactManager.getAllContacts().size());
	}
	
	@Test
	@DisplayName("Phone Number should start with 0")
	public void shouldTestPhoneNumberFormat() {
	    contactManager.addContact("John", "Doe", "0123456789");
	    assertFalse(contactManager.getAllContacts().isEmpty());
	    assertEquals(1, contactManager.getAllContacts().size());
	}
	
	
	@Test
	@DisplayName("Test Should Be Disabled")
	@Disabled
	public void shouldBeDisabled() {
	    throw new RuntimeException("Test Should Not be executed");
	}
	
	@Nested
	class RepeatedNestedClass{
		@DisplayName("Repeat Contact Creation Test 5 Times")
		@RepeatedTest(5)
		public void shouldTestContactCreationRepeatedly() {
		    contactManager.addContact("John", "Doe", "0123456789");
		    assertFalse(contactManager.getAllContacts().isEmpty());
		    assertEquals(1, contactManager.getAllContacts().size());
		}
		
		@DisplayName("Repeat Contact Creation Test 5 Times With Custom Name")
		@RepeatedTest(value = 5,
		        name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
		public void shouldTestContactCreationRepeatedlyWithCustomName() {
		    contactManager.addContact("John", "Doe", "0123456789");
		    assertFalse(contactManager.getAllContacts().isEmpty());
		    assertEquals(1, contactManager.getAllContacts().size());
		}
	}
	
	@Nested
	class ParameterizedNestedClass{
		@DisplayName("Phone Number should match the required Format")
		@ParameterizedTest
		@ValueSource(strings = {"0123456789", "1234567890", "+0123456789"})
		public void shouldTestPhoneNumberFormat(String phoneNumber) {
		    contactManager.addContact("John", "Doe", phoneNumber);
		    assertFalse(contactManager.getAllContacts().isEmpty());
		    assertEquals(1, contactManager.getAllContacts().size());
		}
		
		@DisplayName("Method Source Case - Phone Number should match the required Format")
		@ParameterizedTest
		@MethodSource("phoneNumberList")
		public void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber) {
		    contactManager.addContact("John", "Doe", phoneNumber);
		    assertFalse(contactManager.getAllContacts().isEmpty());
		    assertEquals(1, contactManager.getAllContacts().size());
		}
		 
		private List<String> phoneNumberList() {
		    return Arrays.asList("0123456789", "1234567890", "+0123456789");
		}
		
		
		@DisplayName("CSV File Source Case - Phone Number should match the required Format")
		@ParameterizedTest
		@CsvFileSource(resources = "/data.csv")
		public void shouldTestPhoneNumberFormatUsingCSVFileSource(String phoneNumber) {
		    contactManager.addContact("John", "Doe", phoneNumber);
		    assertFalse(contactManager.getAllContacts().isEmpty());
		    assertEquals(1, contactManager.getAllContacts().size());
		}
	}
	
}
