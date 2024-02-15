import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

/** ==========================Constants============================ **/
// Define the lifetime duration for the spawned process in seconds
val LIFETIME_SEC = 600L
// Define the command to be executed by the application
val COMMAND = listOf("node", "/home/ubuntu/app.js")
/** ================================================================ **/

private class StreamGobbler(inputStream: InputStream, consumer: Consumer<String>) : Runnable {
    private val inputStream: InputStream
    private val consumer: Consumer<String>

    init {
        this.inputStream = inputStream
        this.consumer = consumer
    }

    override fun run() {
        BufferedReader(InputStreamReader(inputStream)).lines()
            .forEach(consumer)
    }
}
fun main() {
    while(true) {
        println("Starting new process")
        var process: Process? = null
        try {
            process = ProcessBuilder().apply {
                COMMAND
            }.start()
            val sg = StreamGobbler(process.inputStream) { x: String? ->
                println(x)
            }
            val future: Future<*> = Executors.newSingleThreadExecutor().submit(sg)
            val exitCode = process.waitFor(LIFETIME_SEC, TimeUnit.SECONDS)
            future.get(LIFETIME_SEC, TimeUnit.SECONDS)
        } catch (e: Exception) {
            println(e)
        } finally {
            process?.destroyForcibly()
        }
    }
}