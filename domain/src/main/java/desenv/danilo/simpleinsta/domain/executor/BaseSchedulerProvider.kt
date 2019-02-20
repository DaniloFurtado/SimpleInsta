package desenv.danilo.simpleinsta.domain.executor

import io.reactivex.Scheduler

interface BaseSchedulerProvider {
    var schedulerExecutor: Scheduler
    var schedulerPost: Scheduler
}