import org.apache.spark.util.StatCounter

class NAStatCounter extends Serializable {
    val stats: org.apache.spark.util.StatCounter = new org.apache.spark.util.StatCounter()
    var missing: Long = 0

    def add(x: Double): NAStatCounter = {
        if (java.lang.Double.isNaN(x)) {
            missing += 1
        } else {
            stats.merge(x)
        }
        this
    }

    def merge(other: NAStatCounter): NAStatCounter = {
        stats.merge(other.stats)
        missing += other.missing
        this
    }

    override def toString = {
        "stats: " + stats.toString + " Nan: " + missing
    }
}

//  object keyword is used to declare a singleton that can
// provide helper methods for a class, analogous to the
// static method definitions on a Java class

// In Scala, apply methods have some special syntactic sugar that
// allows us to call them without having to type them out explicitly;
// for example, these two lines do exactly the same thing:
//         val nastats = NAStatCounter.apply(17.29)
//         val nastats = NAStatCounter(17.29)
object NAStatCounter extends Serializable {
    def apply(x: Double) = new NAStatCounter().add(x)
}

import org.apache.spark.rdd.RDD

def statsWithMissing(rdd: RDD[Array[Double]]): Array[NAStatCounter] = {
    val nastats = rdd.mapPartitions((iter: Iterator[Array[Double]]) => {
        val nas: Array[NAStatCounter] = iter.next().map(d => NAStatCounter(d))
        iter.foreach(arr => {
            nas.zip(arr).foreach { case (n, d) => n.add(d) }
        })
        Iterator(nas)
    })
    nastats.reduce((n1, n2) => {
        n1.zip(n2).map { case (a, b) => a.merge(b) }
    })
}
