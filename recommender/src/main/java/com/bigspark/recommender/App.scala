val rawUserArtistData = sc.textFile("hdfs:///user/bob/ds/user_artist_data.txt")

val rawArtistData = sc.textFile("hdfs:///user/bob/ds/artist_data.txt")

val artisByID = rawArtistData.flatMap { line =>
    val (id, name) = line.span(_ != '\t')
    if (name.isEmpty) {
        None
    } else {
        try {
            Some((id.toInt, name.trim))
        } catch {
            case e: NumberFormatException => None
        }
    }
}


val rawArtistAlias = sc.textFile("hdfs:///user/bob/ds/artist_alias.txt")
val artistAlias = rawArtistAlias.flatMap { line =>
    val tokens = line.split('\t')
    if (tokens(0).isEmpty) {
        None
    } else {
        Some((tokens(0).toInt, tokens(1).toInt))
    }
}.collectAsMap()

// artisByID.lookup(6803336).head
// artisByID.lookup(1000010).head
