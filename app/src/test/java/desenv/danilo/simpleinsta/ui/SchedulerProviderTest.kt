package desenv.danilo.simpleinsta.ui

import desenv.danilo.simpleinsta.util.BaseSchedulerProvider
import io.reactivex.schedulers.TestScheduler

class SchedulerProviderTest: BaseSchedulerProvider {
    private val schedulerTest = TestScheduler()

    override fun io() = schedulerTest

    override fun computarion() = schedulerTest

    override fun mainThread() = schedulerTest

    fun triggerActions(){
        this.schedulerTest.triggerActions()
    }
}