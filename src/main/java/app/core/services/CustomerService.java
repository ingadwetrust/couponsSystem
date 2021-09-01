package app.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.entities.Customer;
import app.core.exceptions.CouponServiceException;
import app.core.exceptions.CouponSystemException;

@Transactional
@Service
@Scope("prototype")
public class CustomerService extends ClientService {

	private Integer customerID;

//	@Autowired
//	private JwtUtil jwt;

	@Override
	public boolean login(String email, String password) {
		Optional<Customer> customerDB = customerRepository.findByEmailAndPassword(email, password);
		if (customerDB.isPresent()) {
			this.customerID = customerDB.get().getId();
			return true;
		}
		return false;
	}

	public Coupon purchaseCoupon(Coupon coupon) throws CouponSystemException {
		Customer actualCustomer = customerRepository.findById(this.customerID).get();
		Optional<Coupon> optionalCoupon = couponRepository.findById(coupon.getId());
		if (optionalCoupon.isPresent()) {
			Coupon couponToPurchase = optionalCoupon.get();
			if (!couponToPurchase.getCustomers().contains(actualCustomer)) {
				System.out.println(couponToPurchase.getCustomers());
				if (couponToPurchase.getAmount() > 0) {
					if (couponToPurchase.getEndDate().isAfter(LocalDate.now())) {
						actualCustomer.getCoupons().add(couponToPurchase);
						couponToPurchase.setAmount(couponToPurchase.getAmount() - 1);
						return couponToPurchase;
					} else
						throw new CouponServiceException("you cannot purchase this coupon since it is expired");
				} else
					throw new CouponServiceException(
							"you cannot purchase this coupon since there are no more coupons of this type available");
			} else
				throw new CouponServiceException(
						"you already own this coupon. you cannot purchase the same coupon twice");
		} else
			throw new CouponServiceException("the coupon you are trying to purchase does not exist in the database");

	}

	public List<Coupon> getAllCoupons() throws CouponSystemException {
		List<Coupon> couponsToReturn = couponRepository.findAll();
		if (!couponsToReturn.isEmpty()) {
			return couponsToReturn;
		} else
			throw new CouponServiceException();
	}

	public List<Coupon> getCustomerCoupons() throws CouponSystemException {
		List<Coupon> couponsToReturn = couponRepository.findByCustomersId(this.customerID);
		if (!couponsToReturn.isEmpty()) {
			return couponsToReturn;
		} else
			throw new CouponServiceException("this customer has no coupons");
	}

	public List<Coupon> getCustomerCoupons(Category category) throws CouponSystemException {
		List<Coupon> couponsToReturn = couponRepository.findByCustomersIdAndCategory(customerID, category);
		if (!couponsToReturn.isEmpty()) {

			return couponsToReturn;
		} else
			throw new CouponServiceException("this customer has no coupons of category" + category.name());
	}

	public List<Coupon> getCustomerCoupons(double price) throws CouponSystemException {
		List<Coupon> couponsToReturn = couponRepository.findByCustomersIdAndPriceLessThanEqual(customerID, price);
		if (!couponsToReturn.isEmpty()) {
			return couponsToReturn;
		} else
			throw new CouponServiceException("this customer has no coupons with price lower than" + price);
	}

	public Customer getCustomerDetails() throws CouponSystemException {

		Optional<Customer> optionalCustomer = customerRepository.findById(this.customerID);
		if (optionalCustomer.isPresent()) {
			return optionalCustomer.get();
		}
		throw new CouponServiceException(
				"getCustomerDetails() failed - Sorry but the customer you are trying to retrieve it does not exist in the database");
	}

	public Coupon getOneCoupon(int couponID) throws CouponSystemException {
		Optional<Coupon> optionalCoupon = couponRepository.findById(couponID);
		if (optionalCoupon.isPresent()) {
			return optionalCoupon.get();
		}
		throw new CouponServiceException("getOneCoupon failed - the coupon does not exist in the database");
	}

}