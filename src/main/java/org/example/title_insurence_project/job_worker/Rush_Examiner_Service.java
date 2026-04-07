package org.example.title_insurence_project.job_worker;

import io.camunda.client.annotation.JobWorker;
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
    public void rushExaminer()
    {
        //get all the rush records
        List<Map<String,Object>> rushRecords=jdbcTemplate.queryForList("SELECT * FROM rush");


    }

}
