package victor.training.patterns.creational.builder;

import org.junit.Test;

public class CustomerValidatorTest {

	private CustomerValidator validator = new CustomerValidator();
	
	@Test
	public void validCustomer_ok() {
		Customer customer = new Customer();
		customer.setFullName("John Doe");
		Address address = new Address();
		address.setCity("Bucharest");
		address.setStreetAddress("Dristor 91");
		customer.setAddress(address);
		validator.validate(customer);
	}

}