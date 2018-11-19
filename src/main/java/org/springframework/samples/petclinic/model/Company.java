package org.springframework.samples.petclinic.model;

/**
 * 企业对象模型
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.rest.JacksonCompanyDeserializer;
import org.springframework.samples.petclinic.rest.JacksonCompanySerializer;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "company")
@JsonSerialize(using = JacksonCompanySerializer.class)
@JsonDeserialize(using = JacksonCompanyDeserializer.class)
public class Company extends NamedEntity {

    @Column(name = "organno")
    @NotEmpty
    private String organno; //组织机构代码（社会统一信用代码）

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date createDate;//创建时间

    @Column(name = "money")
    private Double money;//注册资金

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
    private Set<LegalPerson> persons;//法人

    @Column(name = "address")
    private String address;//公司地址

    public String getOrganno() {
        return organno;
    }

    public void setOrganno(String organno) {
        this.organno = organno;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @JsonIgnore
    protected Set<LegalPerson> getPetsInternal() {
        if (this.persons == null) {
            this.persons = new HashSet<>();
        }
        return this.persons;
    }

    protected void setPetsInternal(Set<LegalPerson> persons) {
        this.persons = persons;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<LegalPerson> getPersons() {
        List<LegalPerson> sortedPersons = new ArrayList<>(getPetsInternal());
        PropertyComparator.sort(sortedPersons, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedPersons);
    }

    public void addPerson(LegalPerson person) {
        getPetsInternal().add(person);
        person.setCompany(this);
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
            .append("id", this.getId())
            .append("name", this.getName())
            .append("organno", this.getOrganno())
            .append("create_date", this.getCreateDate())
            .append("address", this.address)
            .append("money", this.getMoney())
            .toString();
    }
}
