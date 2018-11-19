package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.LegalPerson;

import java.util.Collection;

public interface LegalPersonRepository {

    LegalPerson findById(int id) throws DataAccessException;

    void save(LegalPerson person) throws DataAccessException;

    Collection<LegalPerson> findAll() throws DataAccessException;

    void delete(LegalPerson person) throws DataAccessException;
}
