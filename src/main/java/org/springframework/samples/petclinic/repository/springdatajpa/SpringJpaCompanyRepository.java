package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.repository.CompanyRepository;
import org.springframework.transaction.annotation.Transactional;

@Profile("spring-data-jpa")
public interface SpringJpaCompanyRepository extends CompanyRepository,Repository<Company, Integer> {

    @Modifying
    @Transactional
    @Query("update Company company set company.name=:param_name WHERE company.id =:id")
    void update(@Param("param_name") String name,@Param("id") int id);

}
