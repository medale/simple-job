# Setting up random.txt as Databricks File System (DBFS) Mount
simple-jobs contains random.txt file. Upload to an S3 bucket you
can access and then see Databricks Guide - 02 Product Overview - 10 DB File System - scala for details on how to mount that bucket as /mnt/markus on your shard from
a Notebook:

```
dbutils.fs.mount(s"s3a://$AccessKey:$SecretKey@$AwsBucketName", "/mnt/markus")
```

# Maven Deployment
```
mvn clean install
```

This creates target/simple-job-1.0.0-SNAPSHOT-shaded.jar

# Create Job from Jar
* Jobs - Create Job
     * Enter title
     * Set JAR - Click on "Drop JAR here to upload" button - navigate to target/simple-job-1.0.0-SNAPSHOT-shaded.jar
     * Main class: com.databricks.simple.JavaSpark
* Cluster - edit (create new or existing)
* Under Active Runs, click "Run Now"
* Once Job is completed, under Completed runs, click on "Run 1"
* The Output box should end with:

```
The sample contained 8 lines that started with m.
maddening
machine
marry
mysterious
mature
melted
mourn
memory
```



