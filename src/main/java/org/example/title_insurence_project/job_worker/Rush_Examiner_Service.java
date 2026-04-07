package org.example.title_insurence_project.job_worker;

import io.camunda.client.CamundaClient;
import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Rush_Examiner_Service {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @JobWorker(type ="Rush_Examiner")
    public void rushExaminer(final JobClient client, final ActivatedJob job)
    {
        //get all the rush records
        List<Map<String,Object>> rushRecords=jdbcTemplate.queryForList("SELECT * FROM rush");

        for( Map<String,Object> record:rushRecords)
        {
       String  adharNo=(String)record.get("Adhar_Number");
       List<Map<String,Object>> govRecords=jdbcTemplate.queryForList("SELECT * FROM person_with_property_details where Adhar_Number= ?",adharNo);
           if(!govRecords.isEmpty())
           {
               Map<String,Object> matchedRecord=govRecords.get(0);

               client.newCompleteCommand(job.getKey()).variables(Map.of("rushrecord",record,"matchedrecords",matchedRecord)).send().join();
           }
        }

    }

}
