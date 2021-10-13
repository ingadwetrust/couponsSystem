package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.services.CompanyService;
import app.core.util.CouponModel;
import app.core.util.JwtUtil;
import app.core.util.JwtUtil.UserDetails;
import app.core.util.JwtUtil.UserDetails.UserType;

@RestController
//@CrossOrigin
@RequestMapping("/company")
public class CompanyController extends ClientController {

	@Autowired
	private CompanyService service;
	@Autowired
	JwtUtil jwt;

	@Override
	@PostMapping("/login")
	UserDetails login(@RequestParam String email, @RequestParam String password) {
		try {
			if (service.login(email, password)) {
				UserDetails company = new UserDetails();
				Company comp = service.getCompanyDetails();
				company.setId(String.valueOf(comp.getId()));
				company.setEmail(email);
				company.setName(comp.getName());
				company.setUserType(UserType.COMPANY);
				company.setToken(jwt.generateToken(company));
				return company;
			} else
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	@PostMapping("/addCoupon")
	public Coupon addCoupon(@ModelAttribute CouponModel coupon, @RequestHeader String token) {
		try {
			return service.addCoupon(coupon);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@PutMapping("/updateCoupon")
	public Coupon updateCoupon(@ModelAttribute CouponModel coupon, @RequestHeader String token) {
		try {
			System.out.println(coupon);
			return service.updateCoupon(coupon);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@DeleteMapping("/deleteCoupon")
	public Coupon deleteCoupon(@RequestParam int id, @RequestHeader String token) {
		try {
			return service.deleteCoupon(id);
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

	@GetMapping("/getCompanyCoupons")
	public List<Coupon> getCompanyCoupons(@RequestHeader String token) {
		try {
			return service.getCompanyCoupons();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/getCompanyCouponsByCategory")
	public List<Coupon> getCompanyCoupons(@RequestParam Category category, @RequestHeader String token) {
		try {
			return service.getCompanyCoupons(category);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/getCompanyCouponsByPrice")
	public List<Coupon> getCompanyCoupons(@RequestParam double maxPrice, @RequestHeader String token) {
		try {
			return service.getCompanyCoupons(maxPrice);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/getCompanyDetails")
	public Company getCompanyDetails(@RequestHeader String token) {
		try {
			return service.getCompanyDetails();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}
