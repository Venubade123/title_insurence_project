package org.example.title_insurence_project.job_worker;

import io.camunda.client.CamundaClient;
import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;
import org.springframework.stereotype.Component;

@Component
public class GeneratorID
{
    private CamundaClient camundaClient;

    @JobWorker(type = "unique-id-generate")
    public void generateUniqueParcelId(JobClient jobClient, ActivatedJob activatedJob){
        // Generate a unique parcel ID
        String uniqueParcelId = "Tit-" + (java.util.UUID.randomUUID().toString());

        System.out.printf(uniqueParcelId);

        // Complete the listener job and set the variable
        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(java.util.Map.of("parcelId", uniqueParcelId))
                .send()
                .join();
    }
}
