package victor.training.patterns.creational.builder;

import org.apache.commons.lang.StringUtils;

public class CustomerValidator {

	public void validate(Customer customer) {
		if (StringUtils.isBlank(customer.getFullName())) {
			throw new IllegalArgumentException("Missing customer name");
		}
		validateAddress(customer.getAddress());
		//etc
	}
	
	private void validateAddress(Address address) {
		if (StringUtils.isBlank(address.getCity())) {
			throw new IllegalArgumentException("Missing address city");
		}
		if (StringUtils.isBlank(address.getStreetAddress())) {
			throw new IllegalArgumentException("Missing address street");
		}
	}
}
