package com.databricks.simple;

import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

public class JavaSpark {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JavaSparkContext ctx = getJavaSparkContext();
		//Assumes dbfs mount /mnt/markus that points to bucket containing random.txt file
		//See Databricks Guide - 02 Product Overview - 10 DB File System - scala for details
		//dbutils.fs.mount(s"s3a://$AccessKey:$SecretKey@$AwsBucketName", s"/mnt/$MountName")
		JavaRDD<String> rdd = ctx.textFile("/mnt/markus/random.txt");
		JavaRDD<String> m = rdd.filter(new Function<String,Boolean>() {
			public Boolean call(String line) throws Exception {
			      return line.toLowerCase().startsWith("m");
			    }
		});
		long count = m.count();
		System.out.println("The sample contained " + count + " lines that started with m.");
		//don't do this if data is big
		List<String> mLines = m.collect();
		for(String mLine : mLines) {
			System.out.println(mLine);
		}
	}

	public static JavaSparkContext getJavaSparkContext() {
		SparkConf sparkConf = new SparkConf().setAppName("MySpark");
		sparkConf.set("spark.serializer",
				"org.apache.spark.serializer.KryoSerializer");
		sparkConf.set("spark.hadoop.dfs.replication", "1");
		sparkConf.set("spark.sql.tungsten.enabled", "false");
		sparkConf.set("spark.hadoop.fs.s3a.server-side-encryption-algorithm",
				"AES256");
		sparkConf.set("spark.hadoop.fs.s3a.connection.timeout", "1800000");

		JavaSparkContext ctx = new JavaSparkContext(
				SparkContext.getOrCreate(sparkConf));

		Configuration hadoopConf = ctx.hadoopConfiguration();

		// Map<String, String> env = System.getenv();
		// String awsAccessKeyId = env.get("AWS_ACCESS_KEY");
		// // System.out.println("awsAccessKeyId-----"+awsAccessKeyId);
		//
		// String awsSecretAccessKey = env.get("AWS_SECRET_KEY");
		// // System.out.println("awsSecretAccessKey-----"+awsSecretAccessKey);
		// if (awsAccessKeyId != null && awsSecretAccessKey != null) {
		// conf.set("fs.s3n.awsAccessKeyId", awsAccessKeyId);
		// conf.set("fs.s3n.awsSecretAccessKey", awsSecretAccessKey);
		// conf.set("fs.s3a.access.key", awsAccessKeyId);
		// conf.set("fs.s3a.secret.key", awsSecretAccessKey);
		//
		// }
		//
		hadoopConf.set("mapreduce.output.fileoutputformat.compress","true");
		hadoopConf.set("mapreduce.output.fileoutputformat.compress.codec","org.apache.hadoop.io.compress.GzipCodec");

		return ctx;
	}
}
