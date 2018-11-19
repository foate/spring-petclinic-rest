package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Company;

import java.util.Collection;

public interface CompanyRepository {

    Company findById(int id) throws DataAccessException;

    Company findByName(String name) throws DataAccessException;

    void save(Company company) throws DataAccessException;

    Collection<Company> findAll() throws DataAccessException;

    void update(String str,int id) throws DataAccessException;

    void delete(Company company) throws DataAccessException;

}
