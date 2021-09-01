package app.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponServiceException;
import app.core.exceptions.CouponSystemException;
import app.core.util.CouponModel;

@Service
@Transactional
@Scope("prototype")
public class CompanyService extends ClientService {

	private Integer companyID;

	@Autowired
	private FileStorageService fileStorageService;

	@Override
	public boolean login(String email, String password) {
		Optional<Company> optCompany = companyRepository.findByEmailAndPassword(email, password);
		if (optCompany.isPresent()) {
			this.companyID = optCompany.get().getId();
			return true;
		}
		return false;
	}

	public Coupon addCoupon(CouponModel couponModel) throws CouponSystemException {
		Company actualCompany = companyRepository.findById(companyID).get();
		if (couponRepository.findOneByCompanyIdAndTitle(this.companyID, couponModel.getTitle()).isEmpty()) {
			Coupon coupon = new Coupon();
			System.out.println(couponModel);
			coupon.setAmount(Integer.parseInt(couponModel.getAmount()));
			coupon.setCategory(Category.valueOf(couponModel.getCategory()));
			coupon.setDescription(couponModel.getDescription());
			coupon.setTitle(couponModel.getTitle());
			coupon.setPrice(Integer.parseInt(couponModel.getPrice()));
			coupon.setCompany(actualCompany);
			coupon.setStartDate(LocalDate.parse(couponModel.getStartDate()));
			coupon.setEndDate(LocalDate.parse(couponModel.getEndDate()));
			coupon.setImageName(couponModel.getImage().getOriginalFilename());
			fileStorageService.storeFile(couponModel.getImage());
			if (actualCompany.getCompanyCoupons().add(coupon)) {
				return couponRepository.save(coupon);
			} else
				return null;

		} else
			throw new CouponServiceException("cannot add coupon. this company already this coupon");
	}

	public Coupon updateCoupon(CouponModel coupon) throws CouponSystemException {
		System.out.println(Integer.parseInt(coupon.getId()));
		Optional<Coupon> optionalCoupon = couponRepository.findById(Integer.parseInt(coupon.getId()));

		if (optionalCoupon.isPresent()) {
			Coupon couponToUpdate = couponRepository.findById(Integer.parseInt(coupon.getId())).get();
			couponToUpdate.setAmount(Integer.parseInt(coupon.getAmount()));
			if (coupon.getCategory() != "unedfined") {
				couponToUpdate.setCategory(Category.valueOf(coupon.getCategory()));
			}
			couponToUpdate.setPrice(Double.parseDouble(coupon.getPrice()));
			couponToUpdate.setDescription(coupon.getDescription());
			couponToUpdate.setEndDate(LocalDate.parse(coupon.getEndDate()));
			couponToUpdate.setStartDate(LocalDate.parse(coupon.getStartDate()));
			couponToUpdate.setTitle(coupon.getTitle());
			couponToUpdate.setCompany(getCompanyDetails());
			if (coupon.getImage() != null) {
				couponToUpdate.setImageName(coupon.getImage().getOriginalFilename());
				fileStorageService.storeFile(coupon.getImage());
			}
			return couponToUpdate;
		} else {
			throw new CouponServiceException("the coupon you are trying to update does not exist in the database");
		}
	}

	public Coupon deleteCoupon(int couponID) throws CouponSystemException {
		Coupon couponToDelete = couponRepository.findById(couponID).get();
		if (couponToDelete != null) {
			if (couponToDelete.getCompany().getId() == this.companyID) {
				couponRepository.delete(couponToDelete);
				return couponToDelete;
			} else {
				throw new CouponServiceException(
						"you have not permission to delete this coupon since it does not belong to this company");
			}
		} else {
			throw new CouponServiceException("the coupon you are trying to delete does not exist in the database");
		}
	}

	public Coupon getOneCoupon(int couponId) throws CouponSystemException {
		Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
		if (optionalCoupon.isPresent()) {
			if (optionalCoupon.get().getCompany().getId() == this.companyID) {

				return optionalCoupon.get();
			} else {
				throw new CouponServiceException(
						"you don't have access to this coupon since it does not belong to this company");
			}
		}
		{
			throw new CouponServiceException("the coupon does not exist in the database");
		}
	}

	public List<Coupon> getCompanyCoupons() throws CouponSystemException {
		List<Coupon> couponsToReturn = couponRepository.findByCompanyId(this.companyID);
		if (!couponsToReturn.isEmpty()) {
			return couponsToReturn;
		} else
			throw new CouponServiceException("this company has no coupons");
	}

	public List<Coupon> getCompanyCoupons(Category category) throws CouponSystemException {
		List<Coupon> couponsToReturn = couponRepository.findByCompanyIdAndCategory(this.companyID, category);
		if (!couponsToReturn.isEmpty()) {
			return couponsToReturn;
		} else
			throw new CouponServiceException("this company has no coupons of category" + category.name());
	}

	public List<Coupon> getCompanyCoupons(double price) throws CouponSystemException {
		List<Coupon> couponsToReturn = couponRepository.findByCompanyIdAndPriceLessThanEqual(this.companyID, price);
		if (!couponsToReturn.isEmpty()) {
			return couponsToReturn;
		} else
			throw new CouponServiceException("this company has no coupons with price lower than" + price);
	}

	public Company getCompanyDetails() throws CouponSystemException {
		Optional<Company> companyToReturn = companyRepository.findById(this.companyID);
		if (companyToReturn.isPresent()) {
			return companyToReturn.get();
		}
		return null;
	}
}
