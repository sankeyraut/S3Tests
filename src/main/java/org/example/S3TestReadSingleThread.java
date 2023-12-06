package org.example;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class S3TestReadSingleThread {
    public static void main(String[] args) throws URISyntaxException {

        URI myURI = new URI("https://s3express-usw2-az1.us-west-2.amazonaws.com");

        S3Client client = S3Client.builder()
                .region(Region.US_WEST_2)
                .endpointOverride(myURI)
                .build();

        HeadBucketRequest headRequest = HeadBucketRequest.builder().bucket(Constants.BUCKET_NAME).build();
        client.headBucket(headRequest);
        System.out.println("Connected to " + Constants.BUCKET_NAME);

        for(int i=0;i<50;i++){
            File teamsFile = new File(i + "-teams.json");
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(Constants.BUCKET_NAME)
                    .key(i+"")
                    .build();
            long start = System.nanoTime();
            client.getObject(objectRequest,teamsFile.toPath());
            long end = System.nanoTime();
            System.out.println("Operation completed  "+ (end-start)/1000000+" milliseconds");


        }
        client.close();



    }



}