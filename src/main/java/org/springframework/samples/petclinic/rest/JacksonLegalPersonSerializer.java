package org.springframework.samples.petclinic.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.samples.petclinic.model.*;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

public class JacksonLegalPersonSerializer extends StdSerializer<LegalPerson> {

    public JacksonLegalPersonSerializer(Class<LegalPerson> t) {
        super(t);
    }
    public JacksonLegalPersonSerializer() {
        this(null);
    }

    @Override
    public void serialize(LegalPerson person, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        jgen.writeStartObject(); // person
        if (person.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeNumberField("id", person.getId());
        }
        jgen.writeStringField("name", person.getName());
        jgen.writeStringField("birthday", formatter.format(person.getBirthday()==null?"":person.getBirthday()));

        Company company = person.getCompany();
        jgen.writeObjectFieldStart("company");
        jgen.writeNumberField("id", company.getId());
        jgen.writeStringField("name", company.getName());
        jgen.writeStringField("organno", company.getOrganno()==null?"":company.getOrganno());
        jgen.writeStringField("address", company.getAddress()==null?"":company.getAddress());
        jgen.writeStringField("create_date", formatter.format(company.getCreateDate()==null?"":company.getCreateDate()));
        jgen.writeNumberField("money", company.getMoney()==null?0:company.getMoney());
        jgen.writeEndObject(); // company
        jgen.writeEndObject(); // person
    }
}
