# big_spark

# Prerequisites
* Hadoop
* Spark
* Scala

Follow installation instructions from here to install all 3: https://geekatwork.wordpress.com/2016/04/28/install-hadoop-and-spark-on-a-mac

The first link seem to be missing some ENV variables you are supposed to set. Try also following the instructions here: https://medium.com/@jeremytarling/apache-spark-and-hadoop-on-a-macbook-air-running-osx-sierra-66bfbdb0b6f7

After following both links, spark-shell was not able to start. The fix was here: https://spark.apache.org/docs/latest/hadoop-provided.html.

Basically, both links installed the 'hadoop free' versions of spark. Hence, to start spark, you need to tell spark where your hadoop distribution is so spark can find hadoop.

## Hadoop
### Commands
* */usr/local/hadoop/sbin/start-dfs.sh* - start HDFS
* */usr/local/hadoop/sbin/stop-dfs.sh* - stop HDFS
* */usr/local/hadoop/sbin/start-yarn.sh* - start YARN
* */usr/local/hadoop/sbin/stop-yarn.sh* - stop YARN
* *hdfs dfs -ls /* - list directories at /
* *hdfs dfs -mkdir /user* - Create directory /userstart-yarn.shstart-yarn.sh
* *hdfs dfs -put block_*.csv /user/bobby/linkage* - Put files, in this case CSV, from host directory into Hadoop

## Spark
### Commands
* *spark-shell --master yarn-client* - Start the scala REPL with Spark extentions. We use YARN, the scheduler inside Hadoop as the cluster manager to schedule Spark jobs onto the different nodes
* *spark-shell --master 'local[*]'* - We use the standalone scheduler that comes with Spark as the cluster manager to schedule Spark jobs. The scheduler is running with the number of nodes (as threads) that match the number of cores on our machine. * means match the number of CPU cores

# Upto
Page 42

Aggregations˝
