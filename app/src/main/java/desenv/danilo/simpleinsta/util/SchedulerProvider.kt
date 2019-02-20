package desenv.danilo.simpleinsta.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider: BaseSchedulerProvider {
    override fun io(): Scheduler =  Schedulers.io()

    override fun computarion() =  Schedulers.computation()

    override fun mainThread() = AndroidSchedulers.mainThread()
}