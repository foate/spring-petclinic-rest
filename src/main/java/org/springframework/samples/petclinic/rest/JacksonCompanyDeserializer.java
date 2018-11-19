package org.springframework.samples.petclinic.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.LegalPerson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JacksonCompanyDeserializer extends StdDeserializer<Company> {

    public JacksonCompanyDeserializer() {
        this(null);
    }
    public JacksonCompanyDeserializer(Class<Company> t) {
        super(t);
    }
    @Override
    public Company deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        JsonNode node = parser.getCodec().readTree(parser);
        Company company = new Company();
        int id = node.get("id").asInt();
        String name = node.get("name").asText(null);
        String organno = node.get("organno").asText(null);
        String address = node.get("address").asText(null);
        String create_date = node.get("create_date").asText(null);
        String money = node.get("money").asText(null);

        if (!(id == 0)) {
            company.setId(id);
        }
        company.setName(name);
        company.setOrganno(organno);
        company.setAddress(address);
        try {
            company.setCreateDate(formatter.parse(create_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        company.setMoney(Double.parseDouble(money));
        return company;
    }

}
