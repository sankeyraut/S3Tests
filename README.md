# S3Tests

Set the right java home

Mac:
```
export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-jdk-21.0.1/Contents/Home
export PATH=/Library/Java/JavaVirtualMachines/graalvm-jdk-21.0.1/Contents/Home/bin:$PATH
```

Linux:
```
export JAVA_HOME=/home/ssm-user/graalvm-jdk-21.0.1
export PATH=/home/ssm-user/graalvm-jdk-21.0.1/bin:$PATH
```

Maven build for creating native image
```
mvn -Pnative -Dagent package
```

### Results
#### JDK21
mvn compile exec:java -Dexec.mainClass="org.example.S3TestReadSync"

```
Connected to sankerau--usw2-az1--x-s3
First Object Read from sankerau--usw2-az1--x-s3
File Deleted
Thread S3 Read0 completed in 18 milliseconds
Thread S3 Read6 completed in 150 milliseconds
Thread S3 Read46 completed in 48 milliseconds
Thread S3 Read34 completed in 90 milliseconds
Thread S3 Read38 completed in 78 milliseconds
Thread S3 Read35 completed in 89 milliseconds
Thread S3 Read36 completed in 87 milliseconds
Thread S3 Read32 completed in 100 milliseconds
Thread S3 Read30 completed in 116 milliseconds
Thread S3 Read1 completed in 233 milliseconds
Thread S3 Read3 completed in 226 milliseconds
Thread S3 Read4 completed in 226 milliseconds
Thread S3 Read2 completed in 294 milliseconds
Thread S3 Read5 completed in 290 milliseconds
Thread S3 Read16 completed in 277 milliseconds
Thread S3 Read13 completed in 306 milliseconds
Thread S3 Read25 completed in 274 milliseconds
Thread S3 Read24 completed in 293 milliseconds
Thread S3 Read31 completed in 280 milliseconds
Thread S3 Read27 completed in 302 milliseconds
Thread S3 Read37 completed in 288 milliseconds
Thread S3 Read7 completed in 386 milliseconds
Thread S3 Read9 completed in 380 milliseconds
Thread S3 Read12 completed in 373 milliseconds
Thread S3 Read14 completed in 369 milliseconds
Thread S3 Read10 completed in 380 milliseconds
Thread S3 Read8 completed in 388 milliseconds
Thread S3 Read15 completed in 370 milliseconds
Thread S3 Read11 completed in 381 milliseconds
Thread S3 Read22 completed in 350 milliseconds
Thread S3 Read18 completed in 359 milliseconds
Thread S3 Read19 completed in 357 milliseconds
Thread S3 Read21 completed in 355 milliseconds
Thread S3 Read20 completed in 357 milliseconds
Thread S3 Read23 completed in 352 milliseconds
Thread S3 Read17 completed in 368 milliseconds
Thread S3 Read29 completed in 336 milliseconds
Thread S3 Read28 completed in 339 milliseconds
Thread S3 Read40 completed in 298 milliseconds
Thread S3 Read39 completed in 303 milliseconds
Thread S3 Read42 completed in 287 milliseconds
Thread S3 Read41 completed in 290 milliseconds
Thread S3 Read47 completed in 272 milliseconds
Thread S3 Read26 completed in 347 milliseconds
Thread S3 Read49 completed in 271 milliseconds
Thread S3 Read43 completed in 290 milliseconds
Thread S3 Read44 completed in 291 milliseconds
Thread S3 Read33 completed in 323 milliseconds
Thread S3 Read48 completed in 273 milliseconds
Thread S3 Read45 completed in 292 milliseconds
```

#### Graalvm
Command : ./target/s3test
```
Connected to sxxxxx--usw2-az1--x-s3
First Object Read from sankerau--usw2-az1--x-s3
File Deleted
Thread S3 Read1 completed in 9 milliseconds
Thread S3 Read0 completed in 66 milliseconds
Thread S3 Read2 completed in 66 milliseconds
Thread S3 Read4 completed in 66 milliseconds
Thread S3 Read3 completed in 66 milliseconds
Thread S3 Read5 completed in 66 milliseconds
Thread S3 Read6 completed in 66 milliseconds
Thread S3 Read7 completed in 66 milliseconds
Thread S3 Read8 completed in 66 milliseconds
Thread S3 Read10 completed in 66 milliseconds
Thread S3 Read9 completed in 66 milliseconds
Thread S3 Read13 completed in 66 milliseconds
Thread S3 Read11 completed in 66 milliseconds
Thread S3 Read15 completed in 65 milliseconds
Thread S3 Read12 completed in 66 milliseconds
Thread S3 Read16 completed in 65 milliseconds
Thread S3 Read14 completed in 66 milliseconds
Thread S3 Read17 completed in 65 milliseconds
Thread S3 Read19 completed in 65 milliseconds
Thread S3 Read20 completed in 65 milliseconds
Thread S3 Read22 completed in 65 milliseconds
Thread S3 Read21 completed in 65 milliseconds
Thread S3 Read23 completed in 65 milliseconds
Thread S3 Read25 completed in 64 milliseconds
Thread S3 Read24 completed in 65 milliseconds
Thread S3 Read26 completed in 64 milliseconds
Thread S3 Read27 completed in 64 milliseconds
Thread S3 Read28 completed in 64 milliseconds
Thread S3 Read18 completed in 66 milliseconds
Thread S3 Read31 completed in 64 milliseconds
Thread S3 Read30 completed in 64 milliseconds
Thread S3 Read33 completed in 64 milliseconds
Thread S3 Read32 completed in 64 milliseconds
Thread S3 Read35 completed in 64 milliseconds
Thread S3 Read34 completed in 64 milliseconds
Thread S3 Read37 completed in 64 milliseconds
Thread S3 Read29 completed in 65 milliseconds
Thread S3 Read36 completed in 64 milliseconds
Thread S3 Read39 completed in 64 milliseconds
Thread S3 Read40 completed in 64 milliseconds
Thread S3 Read38 completed in 64 milliseconds
Thread S3 Read42 completed in 64 milliseconds
Thread S3 Read41 completed in 64 milliseconds
Thread S3 Read43 completed in 64 milliseconds
Thread S3 Read45 completed in 64 milliseconds
Thread S3 Read44 completed in 64 milliseconds
Thread S3 Read46 completed in 63 milliseconds
Thread S3 Read47 completed in 64 milliseconds
Thread S3 Read48 completed in 63 milliseconds
Thread S3 Read49 completed in 63 milliseconds
```


