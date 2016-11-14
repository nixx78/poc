package lv.nixx.poc.service;

import lv.nixx.poc.domain.*;
import lv.nixx.poc.exception.UpdateException;

/**
 * Facade interface for BW system, class contains method to process active
 * operations or update entity in system BW. 
 * 
 * @author nixx
 */

public interface BWService {

	public String createPayment(String paymentReference, Payment paymentDetails);
	
	public void updateCustomer(Customer customer) throws UpdateException;
	public void updateAccount(Account account) throws UpdateException;
	public void updateDeposit(Deposit deposit) throws UpdateException;
	
}
