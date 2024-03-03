
# Step1 
```text
Install JDK, MySQL, Maven, Postman and Git
```

# Step2 
```text
Start MySQL Server：
brew services start mysql

Stop MySQL Server：
brew services stop mysql

Connect to Server：
mysql -u root

MySQL Command:
Create DB: product-sale

Add userName and Password as application.properties said
```

# Step3
```text
mvn clean package 
Java -jar target/practical-test-0.0.1-SNAPSHOT.jar
```

# Step 4
```text
Test by Postman, you can find http url and request samples in "Restful API Technique Document.md"
```



[//]: # (-------------------visa part-------------------)
# step1
login into https://developer.visa.com/identity/user/login
username: yczhyou@gmail.com

# step2
"VISA_CONNECTOR" -> "Credentials" to download the cert credentials

# reference
if the cert expired, then should follow this to create a new one: https://developer.visa.com/pages/working-with-visa-apis/two-way-ssl
This is the complete guid for visa development: https://developer.visa.com/pages/working-with-visa-apis/visa-developer-quick-start-guide

