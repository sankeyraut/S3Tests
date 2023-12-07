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
All threads warmed. Running the test again
Thread S3 Read68 completed in 6 milliseconds
Thread S3 Read50 completed in 72 milliseconds
Thread S3 Read51 completed in 74 milliseconds
Thread S3 Read52 completed in 71 milliseconds
Thread S3 Read55 completed in 69 milliseconds
Thread S3 Read57 completed in 68 milliseconds
Thread S3 Read56 completed in 70 milliseconds
Thread S3 Read58 completed in 66 milliseconds
Thread S3 Read59 completed in 67 milliseconds
Thread S3 Read60 completed in 67 milliseconds
Thread S3 Read62 completed in 64 milliseconds
Thread S3 Read63 completed in 65 milliseconds
Thread S3 Read61 completed in 66 milliseconds
Thread S3 Read53 completed in 69 milliseconds
Thread S3 Read64 completed in 65 milliseconds
Thread S3 Read66 completed in 62 milliseconds
Thread S3 Read67 completed in 61 milliseconds
Thread S3 Read65 completed in 64 milliseconds
Thread S3 Read54 completed in 77 milliseconds
Thread S3 Read70 completed in 59 milliseconds
Thread S3 Read71 completed in 58 milliseconds
Thread S3 Read73 completed in 55 milliseconds
Thread S3 Read72 completed in 57 milliseconds
Thread S3 Read74 completed in 54 milliseconds
Thread S3 Read76 completed in 52 milliseconds
Thread S3 Read78 completed in 50 milliseconds
Thread S3 Read75 completed in 56 milliseconds
Thread S3 Read69 completed in 65 milliseconds
Thread S3 Read77 completed in 52 milliseconds
Thread S3 Read81 completed in 50 milliseconds
Thread S3 Read80 completed in 51 milliseconds
Thread S3 Read84 completed in 47 milliseconds
Thread S3 Read82 completed in 51 milliseconds
Thread S3 Read85 completed in 47 milliseconds
Thread S3 Read83 completed in 49 milliseconds
Thread S3 Read88 completed in 45 milliseconds
Thread S3 Read87 completed in 45 milliseconds
Thread S3 Read86 completed in 47 milliseconds
Thread S3 Read91 completed in 44 milliseconds
Thread S3 Read89 completed in 46 milliseconds
Thread S3 Read93 completed in 42 milliseconds
Thread S3 Read90 completed in 46 milliseconds
Thread S3 Read79 completed in 63 milliseconds
Thread S3 Read98 completed in 38 milliseconds
Thread S3 Read95 completed in 65 milliseconds
Thread S3 Read92 completed in 72 milliseconds
Thread S3 Read94 completed in 69 milliseconds
Thread S3 Read96 completed in 65 milliseconds
Thread S3 Read99 completed in 64 milliseconds
Thread S3 Read97 completed in 74 milliseconds
```

#### Graalvm
Command : ./target/s3test
```
All threads warmed. Running the test again
Thread S3 Read51 completed in 9 milliseconds
Thread S3 Read55 completed in 8 milliseconds
Thread S3 Read53 completed in 9 milliseconds
Thread S3 Read58 completed in 7 milliseconds
Thread S3 Read57 completed in 8 milliseconds
Thread S3 Read56 completed in 8 milliseconds
Thread S3 Read52 completed in 9 milliseconds
Thread S3 Read62 completed in 7 milliseconds
Thread S3 Read60 completed in 8 milliseconds
Thread S3 Read64 completed in 7 milliseconds
Thread S3 Read59 completed in 8 milliseconds
Thread S3 Read61 completed in 8 milliseconds
Thread S3 Read63 completed in 8 milliseconds
Thread S3 Read67 completed in 7 milliseconds
Thread S3 Read65 completed in 8 milliseconds
Thread S3 Read68 completed in 7 milliseconds
Thread S3 Read73 completed in 7 milliseconds
Thread S3 Read71 completed in 7 milliseconds
Thread S3 Read66 completed in 8 milliseconds
Thread S3 Read69 completed in 8 milliseconds
Thread S3 Read72 completed in 8 milliseconds
Thread S3 Read54 completed in 11 milliseconds
Thread S3 Read70 completed in 8 milliseconds
Thread S3 Read75 completed in 8 milliseconds
Thread S3 Read78 completed in 7 milliseconds
Thread S3 Read77 completed in 8 milliseconds
Thread S3 Read81 completed in 7 milliseconds
Thread S3 Read76 completed in 8 milliseconds
Thread S3 Read83 completed in 7 milliseconds
Thread S3 Read80 completed in 7 milliseconds
Thread S3 Read79 completed in 8 milliseconds
Thread S3 Read82 completed in 7 milliseconds
Thread S3 Read86 completed in 7 milliseconds
Thread S3 Read85 completed in 7 milliseconds
Thread S3 Read87 completed in 7 milliseconds
Thread S3 Read90 completed in 6 milliseconds
Thread S3 Read89 completed in 7 milliseconds
Thread S3 Read88 completed in 7 milliseconds
Thread S3 Read74 completed in 9 milliseconds
Thread S3 Read94 completed in 6 milliseconds
Thread S3 Read93 completed in 7 milliseconds
Thread S3 Read95 completed in 6 milliseconds
Thread S3 Read98 completed in 6 milliseconds
Thread S3 Read96 completed in 6 milliseconds
Thread S3 Read92 completed in 7 milliseconds
Thread S3 Read97 completed in 6 milliseconds
Thread S3 Read99 completed in 6 milliseconds
Thread S3 Read91 completed in 8 milliseconds
Thread S3 Read50 completed in 16 milliseconds
Thread S3 Read84 completed in 12 milliseconds
```


