package desenv.danilo.simpleinsta.data.util

import io.reactivex.Scheduler

interface BaseSchedulerProvider {
    fun io(): Scheduler
    fun computarion(): Scheduler
    fun mainThread(): Scheduler
}