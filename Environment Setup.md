
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
mysql -u root -p

MySQL Command:
Create DB: practical_test

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

