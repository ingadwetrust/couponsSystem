package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.services.AdminService;
import app.core.util.JwtUtil;
import app.core.util.JwtUtil.UserDetails;
import app.core.util.JwtUtil.UserDetails.UserType;

@RestController
//@CrossOrigin
@RequestMapping("/admin")
public class AdminController extends ClientController {

	@Autowired
	private AdminService service;
	@Autowired
	ConfigurableApplicationContext ctx;
	@Autowired
	JwtUtil jwt;

	public AdminController() {
	}

	@Override
	@PostMapping("/login")
	UserDetails login(@RequestParam String email, @RequestParam String password) {
		try {
			if (service.login(email, password)) {
				UserDetails admin = new UserDetails();
				admin.setEmail(email);
				admin.setName("Admin");
				admin.setUserType(UserType.ADMIN);
				admin.setToken(jwt.generateToken(admin));
				return admin;
			} else
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	@PostMapping(path = "/addCompany")
	public Company addCompany(@RequestBody Company company, @RequestHeader String token) {
		try {
			return service.addCompany(company);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@PutMapping(path = "/updateCompany")
	public Company updateCompany(@RequestBody Company company, @RequestHeader String token) {
		try {
			return service.updateCompany(company);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@DeleteMapping(path = "/deleteCompany")
	public Company deleteCompany(@RequestParam int id, @RequestHeader String token) {
		try {
			return service.deleteCompany(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/getOneCompany")
	public Company getOneCompany(@RequestParam int id, @RequestHeader String token) {
		try {
			return service.getOneCompany(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/getAllCompanies")
	public List<Company> getAllCompanies(@RequestHeader String token) {
		try {
			return service.getAllCompanies();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PostMapping(path = "/addCustomer")
	public Customer addCustomer(@RequestBody Customer customer, @RequestHeader String token) {
		try {
			return service.addCustomer(customer);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@PutMapping(path = "/updateCustomer")
	public Customer updateCustomer(@RequestBody Customer customer, @RequestHeader String token) {
		try {
			return service.updateCustomer(customer);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@DeleteMapping(path = "/deleteCustomer")
	public Customer deleteCustomer(@RequestParam int id, @RequestHeader String token) {
		try {
			return service.deleteCustomer(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/getOneCustomer")
	public Customer getOneCustomer(@RequestParam int id, @RequestHeader String token) {
		try {
			return service.getOneCustomer(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping(path = "/getAllCustomers")
	public List<Customer> getAllCustomers(@RequestHeader String token) {
		try {
			return service.getAllCustomers();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
