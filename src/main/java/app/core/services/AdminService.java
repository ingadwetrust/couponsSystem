package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponServiceException;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional

public class AdminService extends ClientService {

//	@Autowired
//	private JwtUtil jwt;

	public static final String ADMIN_EMAIL = "admin@admin.com";
	public static final String ADMIN_PASSWORD = "admin";

	@Override
	public boolean login(String email, String password) {
		if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
			return true;
		} else
			return false;
	}

	public Company addCompany(Company company) throws CouponSystemException {
		if (companyRepository.findByName(company.getName()).isEmpty()
				&& companyRepository.findByEmail(company.getEmail()).isEmpty()) {
			return companyRepository.save(company);
		} else {

			if (companyRepository.findByName(company.getName()).isPresent()
					&& companyRepository.findByEmail(company.getEmail()).isPresent()) {
				throw new CouponServiceException(
						"addCompany(company) - failed. The company you are trying to add already exists with name: "
								+ company.getName() + " and email: " + company.getEmail());
			} else if (companyRepository.findByName(company.getName()).isPresent()) {
				throw new CouponServiceException(
						"addCompany(company) - failed. The company you are trying to add already exists with name: "
								+ company.getName());
			} else {
				throw new CouponServiceException(
						"addCompany(company) - failed. The company you are trying to add already exists with email: "
								+ company.getEmail());
			}
		}
	}

	public Company updateCompany(Company company) throws CouponSystemException {
		Company companyToUpdate = companyRepository.findById(company.getId()).get();
		if (companyToUpdate != null) {
			companyToUpdate.setEmail(company.getEmail());
			companyToUpdate.setPassword(company.getPassword());
			return companyToUpdate;
		} else
			throw new CouponServiceException("the company you are trying to update does not exist in the database");
	}

	public Company deleteCompany(int companyID) throws CouponSystemException {
		Company companyToDelete = companyRepository.findById(companyID).get();
		if (companyToDelete != null) {
			companyRepository.delete(companyToDelete);
			return companyToDelete;
		} else {
			throw new CouponServiceException("the company you are trying to delete does not exist in the database");
		}
	}

	public Company getOneCompany(int companyID) throws CouponSystemException {
		Optional<Company> company = companyRepository.findById(companyID);
		if (company.isEmpty()) {
			throw new CouponServiceException("the company you are looking for does not exist in the database");
		} else {
			return company.get();
		}
	}

	public List<Company> getAllCompanies() throws CouponSystemException {
		return companyRepository.findAll(Sort.by("name"));
	}

	public Customer addCustomer(Customer customer) throws CouponSystemException {
		if (customerRepository.findByEmail(customer.getEmail()).isEmpty()) {
			return customerRepository.save(customer);
		} else
			throw new CouponServiceException("the customer you are trying to add already exists in the database");

	}

	public Customer updateCustomer(Customer customer) throws CouponSystemException {
		Customer customerToUpdate = customerRepository.findById(customer.getId()).get();
		if (customerToUpdate != null) {
			customerToUpdate.setFirstName(customer.getFirstName());
			customerToUpdate.setLastName(customer.getLastName());
			customerToUpdate.setEmail(customer.getEmail());
			customerToUpdate.setPassword(customer.getPassword());
			return customerToUpdate;
		} else
			throw new CouponServiceException("the company you are trying to update does not exist in the database");
	}

	public Customer deleteCustomer(int customerID) throws CouponSystemException {
		Customer customerToDelete = customerRepository.findById(customerID).get();
		if (customerToDelete != null) {
			customerRepository.delete(customerToDelete);
			return customerToDelete;
		} else {
			throw new CouponServiceException("the company you are trying to delete does not exist in the database");
		}
	}

	public List<Customer> getAllCustomers() throws CouponSystemException {
		return customerRepository.findAll();
	}

	public Customer getOneCustomer(int customerID) throws CouponSystemException {
		Optional<Customer> customer = customerRepository.findById(customerID);
		if (customer.isEmpty()) {
			throw new CouponServiceException("the company you are looking for does not exist in the database");
		} else
			return customer.get();
	}
}
