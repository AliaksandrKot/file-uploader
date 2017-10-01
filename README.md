# File Uploader

File uploader was implemented accordinf of specification.
There are a lot of ways for improvments. 
For example: 
  - Testing
  - Some improvments of compression and encryption
  - Improve file name generator

Run for building jar file: 
```sh
./mvn clean install
```
After that execute for runnign application:
```sh
java -jar target/upload-0.0.1-SNAPSHOT.jar 
```
# Features:

### Simple file upload
    
  - Just upload file
### Upload file with compession
    
  - Upload file with compressing to gzip. For activating this functionality provide parameters like query param 
```sh
isCompress = true
```

### Upload file with encryption
    
  - Upload file with encryptin wuth AES. For activating this functionality provide parameters like query param.
```sh
isEncrypt = true
```

You can also use both of compressing and encryption