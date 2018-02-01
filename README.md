# big_spark

# Prerequisites
* Hadoop
* Spark
* Scala

Follow installation instructions from here to install all 3: https://geekatwork.wordpress.com/2016/04/28/install-hadoop-and-spark-on-a-mac

The first link seem to be missing some ENV variables you are supposed to set. Try also following the instructions here: https://medium.com/@jeremytarling/apache-spark-and-hadoop-on-a-macbook-air-running-osx-sierra-66bfbdb0b6f7

## Hadoop
### Commands
* */usr/local/hadoop/sbin/start-dfs.sh* - start HDFS
* */usr/local/hadoop/sbin/stop-dfs.sh* - stop HDFS
* */usr/local/hadoop/sbin/start-yarn.sh* - start YARN
* */usr/local/hadoop/sbin/stop-yarn.sh* - stop YARN
* *hdfs dfs -ls /* - list directories at /
* *hdfs dfs -mkdir /user* - Create directory /userstart-yarn.shstart-yarn.sh


# Upto
Page 27

Getting Started: The Spark Shell and SparkContext

Before that: Followed the two link above to install Hadoop, Spark, and Scala. However, when using *spark-shell* from the command line, it throws a java exception. Find out why and get spark working
