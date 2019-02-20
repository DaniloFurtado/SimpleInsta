package desenv.danilo.simpleinsta.presentation.executor

import desenv.danilo.simpleinsta.domain.executor.BaseSchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ShchedulerProvider: BaseSchedulerProvider {
    override var schedulerExecutor = Schedulers.io()
    override var schedulerPost= AndroidSchedulers.mainThread()
}