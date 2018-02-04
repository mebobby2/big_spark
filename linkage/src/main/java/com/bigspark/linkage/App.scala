package com.bigspark.linkage

// Steps inside spark-shell
val rawblocks = sc.textFile("hdfs:/user/bob/linkage")

def isHeader(line: String) = line.contains("id_1")
val noheader = rawblocks.filter(x => !isHeader(x))

case class MatchData(id1: Int, id2: Int, scores: Array[Double], matched: Boolean)

def toDouble(s: String) = {
    if ("?".equals(s)) Double.NaN else s.toDouble
}

def parse(line: String) = {
    val pieces = line.split(',')
    val id1 = pieces(0).toInt
    val id2 = pieces(1).toInt
    val scores = pieces.slice(2, 11).map(toDouble)
    val matched = pieces(11).toBoolean
    MatchData(id1, id2, scores, matched)
}

val parsed = noheader.map(line => parse(line))

// import java.lang.Double.isNaN
// parsed.map(md => md.scores(0)).filter(!isNaN(_)).stats()

:load StatsWithMissing.scala

// val nasRDD = parsed.map(md => {
//     md.scores.map(d => NAStatCounter(d))
// })

// val reduced = nasRDD.reduce((n1, n2) => {
//     n1.zip(n2).map { case(a, b) => a.merge(b) }
// })
// reduced.foreach(println)

val statsm = statsWithMissing(parsed.filter(_.matched).map(_.scores))
val statsn = statsWithMissing(parsed.filter(!_.matched).map(_.scores))

statsm.zip(statsn).map { case(m, n) =>
    (m.missing + n.missing, m.stats.mean - n.stats.mean)
}.foreach(println)

def naz(d: Double) = if (Double.NaN.equals(d)) 0.0 else d
case class Scored(md: MatchData, score: Double)
val ct = parsed.map(md => {
    val score = Array(2, 5, 6, 7, 8).map(i => naz(md.scores(i))).sum
    Scored(md, score)
})

ct.filter(s => s.score >= 4.0).map(s => s.md.matched).countByValue()
