package com.warehouse;

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
  String SECRETS_PROD_BIN = "database/prod";
  String SECRETS_PROD_ACCESS_KEY = "AKIAQ2CZVFGCT4YUUOU6";
  String SECRETS_PROD_SECRET_KEY = "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";
  String SECRETS_TEST_BIN = "database/test";
  String SECRETS_TEST_ACCESS_KEY = "AKIAQ2CZVFGCT4YUUOU6";
  String SECRETS_TEST_SECRET_KEY = "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";

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
      dbcredentials = getSecrets(SECRETS_PROD_ACCESS_KEY, SECRETS_PROD_SECRET_KEY, SECRETS_PROD_BIN);
      logger.info("Loaded PRODUCTION database credentials from AWS Secrets.", SecretManager.class.getSimpleName());
    
    } catch (Exception e){

      // fall back to dev environment
      try { 
        dbcredentials = getSecrets(SECRETS_TEST_ACCESS_KEY, SECRETS_TEST_SECRET_KEY, SECRETS_TEST_BIN);
        logger.info("Loaded TEST database credentials from AWS Secrets.", SecretManager.class.getSimpleName());      
      }catch (Exception ee){
        logger.error("Unable to load database credentials from AWS Secrets.", SecretManager.class.getSimpleName());
      }
      
    }
    
    this.databaseHost = dbcredentials.get("hibernate_db_host").toString();
    this.databaseName = dbcredentials.get("hibernate_db_name").toString();
    this.databasePass = dbcredentials.get("hibernate_db_pass").toString();
    this.databaseUser = dbcredentials.get("hibernate_db_user").toString();
  }

  public JSONObject getSecrets(String accessKey, String secretKey, String secretName) 
    throws Exception{
    
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