// Your username to login to Databricks
dbcUsername := "your cluster username"

// Your password (Can be set as an environment variable)
dbcPassword := System.getenv("DBCLOUD_PASSWORD")

// Gotcha: Setting environment variables in IDE's may differ. IDE's usually don't pick up environment variables from .bash_profile or .bashrc

// The URL to the Databricks REST API.
dbcApiUrl := "https://<YOUR_INSTANCE>.cloud.databricks.com/api/1.2"

// Add any clusters that you would like to deploy your work to. e.g. "My Cluster"
dbcClusters += "YOUR CLUSTER" 

// An optional parameter to set the location to upload your libraries to in the workspace e.g. "/home/USER/libraries"
// This location must be an existing path.
// NOTE: Specifying this parameter is *strongly* recommended as many jars will be uploaded to your cluster.
// Putting them in one folder will make it easy for your to delete all the libraries at once.
// Create Libraries folder in your Folder
dbcLibraryPath := "/Users/<YOUR CLUSTER USERNAME>/Libraries"

// Whether to restart the clusters everytime a new version is uploaded to Databricks.
dbcRestartOnAttach := false // Default true

name := "SimpleJob"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.1" % "provided"

