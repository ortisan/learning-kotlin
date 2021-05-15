import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// *** Its a copy os golang goroutines (https://tour.golang.org/concurrency/1)
class Coroutines {

    fun greetRunBlocking() = runBlocking { // this: CoroutineScope
        launch { // launch a new coroutine and continue
            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
            println("World!") // print after delay
        }
        println("Hello") // main coroutine continues while a previous one is delayed
    }
}

fun main(args: Array<String>) {
    Coroutines().greetRunBlocking()
}

