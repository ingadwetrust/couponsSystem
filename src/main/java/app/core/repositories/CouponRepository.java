package app.core.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	// @Query(value = "select * from coupons where company_id = :companyID and
	// coupon_title = :title", nativeQuery = true)
	List<Coupon> findAllByEndDateBefore(Date endDate) throws CouponSystemException;

	List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyID, double price) throws CouponSystemException;

	List<Coupon> findByCompanyIdAndCategory(int companyID, Category category) throws CouponSystemException;

	Optional<Coupon> findOneByCompanyIdAndTitle(int companyID, String title) throws CouponSystemException;

	Optional<Coupon> findOneByIdAndCompanyId(int companyID, int companyId) throws CouponSystemException;

	List<Coupon> findByCompanyId(int companyID) throws CouponSystemException;

	List<Coupon> findByCustomersId(int customerID) throws CouponSystemException;

	List<Coupon> findByCustomersIdAndCategory(int customerID, Category category) throws CouponSystemException;

	List<Coupon> findByCustomersIdAndPriceLessThanEqual(int customerID, double price) throws CouponSystemException;

	void deleteAllByEndDateBefore(LocalDate endDate) throws CouponSystemException;
}