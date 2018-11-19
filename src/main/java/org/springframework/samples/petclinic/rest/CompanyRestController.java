package org.springframework.samples.petclinic.rest;

import antlr.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.LegalPerson;
import org.springframework.samples.petclinic.service.CompanyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/companys")
public class CompanyRestController {

    @Autowired
    private CompanyService companyService;

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Company>> getCompanys() {
        Collection<Company> companys = this.companyService.findAll();
        if (companys.isEmpty()) {
            return new ResponseEntity<Collection<Company>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Company>>(companys, HttpStatus.OK);
    }


    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
    @RequestMapping(value = "/{companyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getCompanyById(@PathVariable("companyId") int companyId) {
        Company company = null;
        company = this.companyService.findCompanyById(companyId);
        if (company == null) {
            return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
    @RequestMapping(value = "/param", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getCompanyByIdParam(@RequestParam("companyId") int companyId) {
        Company company = null;
        List<Company> companys=new ArrayList<Company>();
        company = this.companyService.findCompanyById(companyId);
        companys.add(company);
        if (company == null) {
            return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
    @RequestMapping(value = "/str/{companyName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> getCompanyByName(@PathVariable("companyName") String companyName) {
        Company company = null;
        company = this.companyService.findCompanyByName(companyName);
        if (company == null) {
            return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> addCompany(@RequestBody @Valid Company company, BindingResult bindingResult,
                                              UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (company == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Company>(headers, HttpStatus.BAD_REQUEST);
        }
        this.companyService.saveCompany(company);
        headers.setLocation(ucBuilder.path("/api/companys/{id}").buildAndExpand(company.getId()).toUri());
        return new ResponseEntity<Company>(company, headers, HttpStatus.CREATED);
    }

    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
    @RequestMapping(value = "/{companyId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Company> updateCompany(@PathVariable("companyId") int companyId, @RequestBody @Valid Company company,
                                             BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (company == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Company>(headers, HttpStatus.BAD_REQUEST);
        }
        Company currentCompany = this.companyService.findCompanyById(companyId);
        if (currentCompany == null) {
            return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
        }
        currentCompany.setAddress(company.getAddress());
        currentCompany.setMoney(company.getMoney());
        currentCompany.setCreateDate(company.getCreateDate());
        currentCompany.setOrganno(company.getOrganno());
        currentCompany.setName(company.getName());

        this.companyService.saveCompany(currentCompany);
        return new ResponseEntity<Company>(currentCompany, HttpStatus.NO_CONTENT);
    }


    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
    @RequestMapping(value = "/{companyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> deleteCompany(@PathVariable("companyId") int companyId) {
        Company company = this.companyService.findCompanyById(companyId);
        if (company == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.companyService.deleteCompany(company);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
