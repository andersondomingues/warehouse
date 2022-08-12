#!/bin/bash
java -jar ./target/warehouse-backend-0.0.1-SNAPSHOT.jar -Dhibernate_db_user=postgres -Dhibernate_db_pass=CED3rnHDra9I5QlcBUCU -Dhibernate_db_host=database-warehouse-prod.cwd25dfzkdvu.sa-east-1.rds.amazonaws.com -Dhibernate_db_name=warehouse 


java -jar ./target/warehouse-backend-0.0.1-SNAPSHOT.jar \
  -Dhibernate_db_user="postgres" \
  -Dhibernate_db_pass="CED3rnHDra9I5QlcBUCU" \
  -Dhibernate_db_host="database-warehouse-prod.cwd25dfzkdvu.sa-east-1.rds.amazonaws.com" \
  -Dhibernate_db_name="warehouse" 

java \
  -Daws_bin_test=database/test \ 
  -Daws_key_test=AKIAQ2CZVFGCT4YUUOU6 \ 
  -Daws_secret_test=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 \ 
  -Daws_bin_prod=database/test \ 
  -Daws_key_prod=AKIAQ2CZVFGCT4YUUOU6 \ 
  -Daws_secret_prod=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 \ 
  -jar .\target\warehouse-backend-0.0.1-SNAPSHOT.jar \