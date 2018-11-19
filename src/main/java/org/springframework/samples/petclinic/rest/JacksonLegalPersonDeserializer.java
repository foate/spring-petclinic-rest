package org.springframework.samples.petclinic.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.LegalPerson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JacksonLegalPersonDeserializer extends StdDeserializer<LegalPerson> {
    public JacksonLegalPersonDeserializer(Class<LegalPerson> vc) {
        super(vc);
    }
    public JacksonLegalPersonDeserializer() {
        this(null);
    }
    @Override
    public LegalPerson deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        LegalPerson person = new LegalPerson();
        Company company = new Company();
        ObjectMapper mapper = new ObjectMapper();
        Date birthDate = null;
        JsonNode node = parser.getCodec().readTree(parser);
        JsonNode company_node = node.get("company");
        company = mapper.treeToValue(company_node, Company.class);
        int personId = node.get("id").asInt();
        String name = node.get("name").asText(null);
        String sex=node.get("sex").asText(null);
        String nation=node.get("nation").asText(null);
        String persionId=node.get("persionId").asText(null);
        String birthDateStr = node.get("birthday").asText(null);
        String education = node.get("education").asText(null);
        try {
            birthDate = formatter.parse(birthDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        if (!(personId == 0)) {
            person.setId(personId);
        }
        person.setName(name);
        person.setBirthday(birthDate);
        person.setCompany(company);
        person.setSex(sex);
        person.setNation(nation);
        person.setPersionId(persionId);
        person.setEducation(education);
        return person;
    }
}
