package desenv.danilo.simpleinsta.util

import io.reactivex.Scheduler

interface BaseSchedulerProvider {
    fun io(): Scheduler
    fun computarion(): Scheduler
    fun mainThread(): Scheduler
}