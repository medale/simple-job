package com.databricks.simple

import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object SimpleSpark {

  def main(args: Array[String]): Unit = {
    val jsc = getJavaSparkContext()
    val rdd = jsc.textFile("/mnt/markus/roles.csv")
    //rdd.filter(line => line.contains("President")) 
    println(s"The count is ${rdd.count()}")
  }

  def getJavaSparkContext(): JavaSparkContext = {
    val sparkConf = new SparkConf().setAppName("MySpark")
    sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
    sparkConf.set("spark.hadoop.dfs.replication", "1");
    sparkConf.set("spark.sql.tungsten.enabled", "false");
    sparkConf.set("spark.hadoop.fs.s3a.server-side-encryption-algorithm", "AES256");
    sparkConf.set("spark.hadoop.fs.s3a.connection.timeout", "1800000");

    val ctx = new JavaSparkContext(SparkContext.getOrCreate(sparkConf));

    val hadoopConf = ctx.hadoopConfiguration()

    //Map<String, String> env = System.getenv(); 
    //String awsAccessKeyId = env.get("AWS_ACCESS_KEY"); 
    //// System.out.println("awsAccessKeyId-----"+awsAccessKeyId);
    //
    //String awsSecretAccessKey = env.get("AWS_SECRET_KEY"); 
    //// System.out.println("awsSecretAccessKey-----"+awsSecretAccessKey); 
    //if (awsAccessKeyId != null && awsSecretAccessKey != null) { 
    //conf.set("fs.s3n.awsAccessKeyId", awsAccessKeyId); 
    //conf.set("fs.s3n.awsSecretAccessKey", awsSecretAccessKey); 
    //conf.set("fs.s3a.access.key", awsAccessKeyId); 
    //conf.set("fs.s3a.secret.key", awsSecretAccessKey);
    //
    //}
    //
    //conf.set("mapreduce.output.fileoutputformat.compress","true"); 
    //conf.set("mapreduce.output.fileoutputformat.compress.codec","org.apache.hadoop.io.compress.GzipCodec"); 

    return ctx;
  }
}