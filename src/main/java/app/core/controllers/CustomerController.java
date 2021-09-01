package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.entities.Customer;
import app.core.services.CustomerService;
import app.core.util.JwtUtil;
import app.core.util.JwtUtil.UserDetails;
import app.core.util.JwtUtil.UserDetails.UserType;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController extends ClientController {

	@Autowired
	private CustomerService service;
	@Autowired
	JwtUtil jwt;

	@Override
	@PostMapping("/login")
	UserDetails login(@RequestParam String email, @RequestParam String password) {
		try {
			if (service.login(email, password)) {
				UserDetails customer = new UserDetails();
				Customer cust = service.getCustomerDetails();
				customer.setId(String.valueOf(cust.getId()));
				customer.setEmail(email);
				customer.setName(cust.getFirstName() + " " + cust.getLastName());
				customer.setUserType(UserType.CUSTOMER);
				customer.setToken(jwt.generateToken(customer));
				return customer;
			} else
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	@PostMapping("/purchaseCoupon")
	public Coupon purchaseCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {
		try {
			return service.purchaseCoupon(coupon);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@GetMapping("/getAllCoupons")
	public List<Coupon> getAllCoupons(@RequestHeader String token) {
		try {
			return service.getAllCoupons();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/getCustomerCoupons")
	public List<Coupon> getCustomerCoupons(@RequestHeader String token) {
		try {
			return service.getCustomerCoupons();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/getCustomerCouponsByCategory")
	public List<Coupon> getCustomerCoupons(@RequestParam Category category, @RequestHeader String token) {
		try {
			return service.getCustomerCoupons(category);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/getCustomerCouponsByPrice")
	public List<Coupon> getCustomerCoupons(@RequestParam double maxPrice, @RequestHeader String token) {
		try {
			return service.getCustomerCoupons(maxPrice);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/getCustomerDetails")
	public Customer getCompanyDetails(@RequestHeader String token) {
		try {
			return service.getCustomerDetails();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/getOneCoupon/{id}")
	public Coupon getOneCoupon(@PathVariable int id, @RequestHeader String token) {
		try {
			return service.getOneCoupon(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}
