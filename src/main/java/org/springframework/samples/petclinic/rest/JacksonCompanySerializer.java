package org.springframework.samples.petclinic.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.samples.petclinic.model.*;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

public class JacksonCompanySerializer extends StdSerializer<Company> {

    public JacksonCompanySerializer()  {
        this(null);
    }

    public JacksonCompanySerializer(Class<Company> t) {
        super(t);
    }

    @Override
    public void serialize(Company company, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        jgen.writeStartObject();
        if (company.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeNumberField("id", company.getId());
        }
        jgen.writeStringField("name", company.getName());
        jgen.writeStringField("organno", company.getOrganno()==null?"":company.getOrganno());
        jgen.writeStringField("address", company.getAddress()==null?"":company.getAddress());
        jgen.writeStringField("create_date", formatter.format(company.getCreateDate()==null?"":company.getCreateDate()));
        jgen.writeNumberField("money", company.getMoney()==null?0:company.getMoney());
        // write persons array
        jgen.writeArrayFieldStart("persons");
        for (LegalPerson person : company.getPersons()) {
            jgen.writeStartObject(); // person
            if (person.getId() == null) {
                jgen.writeNullField("id");
            } else {
                jgen.writeNumberField("id", person.getId());
            }
            jgen.writeStringField("name", person.getName());
            jgen.writeStringField("birthday", formatter.format(person.getBirthday()==null?"":person.getBirthday()));
            jgen.writeStringField("sex", person.getSex()==null?"":person.getSex());
            jgen.writeStringField("nation", person.getNation()==null?"":person.getNation());
            jgen.writeStringField("education", person.getEducation()==null?"":person.getEducation());
            jgen.writeStringField("persion_id", person.getPersionId()==null?"":person.getPersionId());
            jgen.writeNumberField("company_id", person.getCompany().getId()==null?0:person.getCompany().getId());
            jgen.writeEndObject(); // person
        }
        jgen.writeEndArray(); // persons
        jgen.writeEndObject(); // company
    }
}
