package lv.nixx.poc.integration;

import lv.nixx.poc.domain.Payment;
import lv.nixx.poc.exception.IntegrationException;

/**
 * Integrator interface, provides business methods to create operations
 * in system BW
 *  
 * @author nixx
 *
 */
public interface Integrator {
	
	public String processPayment(Payment paymentDetails);
	public void connectCustomerToBW(String customerID) throws IntegrationException;
	
}
