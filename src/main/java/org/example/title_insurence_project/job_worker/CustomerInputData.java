package org.example.title_insurence_project.job_worker;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomerInputData {

    @Autowired
    private  JdbcTemplate jdbcTemplate;

//    public CustomerInputData(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    @JobWorker(type = "store-form-data")
    public void handleForm(final JobClient client, final ActivatedJob job) {
        Map<String, Object> variables = job.getVariablesAsMap();
        String priorityType = (String) variables.get("priorityType");

        Object[] params = {
                variables.get("Owner_Name"),
                variables.get("Property_Adress"),
                variables.get("email"),
                variables.get("parcelId"),
                variables.get("State"),
                variables.get("Country"),
                variables.get("choose_policy"),
                variables.get("Adhar_Number"),
                priorityType,
                variables.get("Process_Fee"),
                variables.get("Estimated_date")
        };

        System.out.println(params);


        if ("required".equalsIgnoreCase(priorityType))
        {
            jdbcTemplate.update(
                    "INSERT INTO rush (owner_name, property_address, email, parcel_id, state, country, choose_policy, adhar_number, priorityType, process_fee, estimated_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    params);
            variables.put("targetQueue", "rush");
        }
        else
        {
            jdbcTemplate.update(
                    "INSERT INTO inbox (owner_name, property_address, email, parcel_id, state, country, choose_policy, adhar_number, priorityType, process_fee, estimated_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    params);
            variables.put("targetQueue", "inbox");

        }
    }
    }
