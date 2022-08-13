package com.warehouse;

import org.apache.naming.ServiceRef;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

/**
 * This class retrieves database credentials from the AWS Secrets vault. 
 * EXPLANATION:
 *     There are two distinct users for accessing keys within AWS Secrets; 
 *     one of them for development credentials, and another one for production
 *     credential. Production credentials user can access the keys only from
 *     inside the AWS domain, so its credentials are invalid during prodution.
 *     For this reason, the credentials are exposed in the code and have no 
 *     effect outside the AWS network domain. 
 */
public class SecretManager {

  String SECRETS_REGION = "sa-east-1";

  private final String SECRETS_BIN = System.getProperty("aws_bin");
  private final String SECRETS_ACCESS_KEY = System.getProperty("aws_key");
  private final String SECRETS_SECRET_KEY = System.getProperty("aws_secret");

  private static SecretManager _instance = null;

  private String databaseHost = null;
  private String databaseName = null;
  private String databaseUser = null;
  private String databasePass = null;

  public String getDatabaseName(){
    return this.databaseName;
  }

  public String getDatabaseHost(){
    return this.databaseHost;
  }

  public String getDatabaseUser(){
    return this.databaseUser;
  }
  
  public String getDatabasePass(){
    return this.databasePass;
  }

  public static SecretManager getInstance() throws Exception{
    if (_instance == null){
      _instance = new SecretManager();
    }
    return _instance;
  }

  private SecretManager() {
    
    JSONObject dbcredentials = null;

    Logger logger = LoggerFactory.getLogger(SecretManager.class);

    try {
      // Try to get production variables once. If outside the production
      // environment, variables won't be available.
      dbcredentials = getSecrets(SECRETS_ACCESS_KEY, SECRETS_SECRET_KEY, SECRETS_BIN);
      this.databaseHost = dbcredentials.get("hibernate_db_host").toString();
      this.databaseName = dbcredentials.get("hibernate_db_name").toString();
      this.databasePass = dbcredentials.get("hibernate_db_pass").toString();
      this.databaseUser = dbcredentials.get("hibernate_db_user").toString();
      
      String type = this.databaseHost.contains("prod") ? "PRODUCTION" : "TEST";
      logger.info("Loaded " + type  + "database credentials from AWS Secrets.", SecretManager.class.getSimpleName());
    
    } catch (Exception e){ 

      logger.error("Unable to load database credentials from AWS Secrets.", SecretManager.class.getSimpleName());
      
    }
  }

  public JSONObject getSecrets(String accessKey, String secretKey, String secretName) 
    throws Exception{
    
    System.out.println("KEY:" + accessKey);
    System.out.println("SCR:" + secretKey);
    System.out.println("BIN:" + secretName);

    BasicAWSCredentials credentials 
      = new BasicAWSCredentials(accessKey, secretKey);
    
    AWSStaticCredentialsProvider provider
      = new AWSStaticCredentialsProvider(credentials);

    AWSSecretsManagerClientBuilder builder = AWSSecretsManagerClientBuilder.standard();
    builder.setCredentials(provider);

    AWSSecretsManager client  = builder.withRegion(SECRETS_REGION).build();
 
    try {
      GetSecretValueResult getSecretValueResult
        = client.getSecretValue(new GetSecretValueRequest().withSecretId(secretName));

      if (getSecretValueResult.getSecretString() != null) {
        String secret = getSecretValueResult.getSecretString();
  
        try {
          JSONObject jsonObject = new JSONObject(secret);
          return jsonObject;
        }catch (JSONException err){
          throw new Exception("Enable to parse recovered secrets from AWS Secrets.");
        } 
        
      }else {
        throw new Exception("Enable to recover secrets from AWS Secrets.");
      }

    } catch (Exception e) {
      throw new Exception("Enable to reach secret bin from AWS Secrets.");
    }


  }
}