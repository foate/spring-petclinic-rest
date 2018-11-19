package org.springframework.samples.petclinic.service;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Company;

import java.util.Collection;

public interface CompanyService {
    Company findCompanyById(int id) throws DataAccessException;
    Company findCompanyByName(String companyName) throws DataAccessException;
    Collection<Company> findAll() throws DataAccessException;
    void saveCompany(Company company) throws DataAccessException;
    void deleteCompany(Company company) throws DataAccessException;
}
