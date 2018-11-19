package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.repository.CompanyRepository;
import org.springframework.samples.petclinic.repository.LegalPersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private LegalPersonRepository legalPersonRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              LegalPersonRepository legalPersonRepository){
        this.companyRepository=companyRepository;
        this.legalPersonRepository=legalPersonRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Company findCompanyById(int id) throws DataAccessException {
        Company company = null;
        try {
            company = companyRepository.findById(id);
        } catch (ObjectRetrievalFailureException |EmptyResultDataAccessException e) {
            // just ignore not found exceptions for Jdbc/Jpa realization
            return null;
        }
        return company;
    }

    @Override
    @Transactional(readOnly = true)
    public Company findCompanyByName(String companyName) throws DataAccessException {
        Company company = null;
        try {
            company = companyRepository.findByName(companyName);
        } catch (ObjectRetrievalFailureException |EmptyResultDataAccessException e) {
            // just ignore not found exceptions for Jdbc/Jpa realization
            return null;
        }
        return company;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Company> findAll() throws DataAccessException {
        return companyRepository.findAll();
    }

    @Override
    @Transactional
    public void saveCompany(Company company) throws DataAccessException {
        companyRepository.save(company);
    }

    @Override
    @Transactional
    public void deleteCompany(Company company) throws DataAccessException {
        companyRepository.delete(company);
    }
}
