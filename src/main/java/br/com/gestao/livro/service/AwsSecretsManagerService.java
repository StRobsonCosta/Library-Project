package br.com.gestao.livro.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Service
public class AwsSecretsManagerService {

	@Value("${aws.region}")
    private String awsRegion;

    public AwsBasicCredentials getAwsCredentials(String secretName) {
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.of(awsRegion))
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse = client.getSecretValue(getSecretValueRequest);

        String secretString = getSecretValueResponse.secretString();

        // Parse the secret string to retrieve access key and secret key
        // Assuming the secret string is a JSON object with keys "accessKeyId" and "secretAccessKey"
        JSONObject secretJson = new JSONObject(secretString);
        String accessKeyId = secretJson.getString("accessKeyId");
        String secretAccessKey = secretJson.getString("secretAccessKey");

        return AwsBasicCredentials.create(accessKeyId, secretAccessKey);
    }
}
