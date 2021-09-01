package app.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Optional<Company> findByEmailAndPassword(String companyEmail, String companyPassword);

	Optional<Company> findByName(String companyName);

	Optional<Company> findByEmail(String companyEmail);

}
