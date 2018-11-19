package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.repository.CompanyRepository;
import org.springframework.samples.petclinic.service.clinicService.ApplicationTestConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy
 *         Simple test to make sure that Bean Validation is working
 *         (useful when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */



@SpringBootTest
@RunWith(SpringRunner.class)
public class ValidatorTests {

    @Autowired
    CompanyRepository cr;

    /*
    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }
    */

    @Test
    public void shouldNotValidateWhenFirstNameEmpty() throws Exception {

        Company company =new Company();
        company.setName("山东省监管局");
        company.setOrganno("20181117");
        company.setAddress("山东省济南市");
       //company.setId(2);
        company.setCreateDate(new Date());
        LegalPerson person1=new LegalPerson();
        person1.setName("工商部门");
        person1.setPersionId("0001");
        person1.setBirthday(new Date());
        LegalPerson person2=new LegalPerson();
        person2.setPersionId("0002");
        person2.setBirthday(new Date());
        person2.setName("质监部门");
        company.addPerson(person1);
        company.addPerson(person2);
        cr.save(company);
        //cr.update("彭长青-foate",2);
        /*
        Company temp=cr.findById(1);
        System.out.println("+++++"+temp.getId()+"   "+temp.getOrganno()+" "+temp.getAddress());
        */
        /*
        List<Company> list=(List<Company>)cr.findAll();
        Company forList=(Company)list.get(0);
        System.out.println("forList:"+forList.getId()+"    "+forList.getOrganno()+"    "+forList.getAddress());
        */
        /*
        company.setId(1);
        cr.delete(company);
        */
        /*
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("");
        person.setLastName("smith");

        Validator validator = createValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Person> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
        assertThat(violation.getMessage()).isEqualTo("may not be empty");
        */
    }

}
