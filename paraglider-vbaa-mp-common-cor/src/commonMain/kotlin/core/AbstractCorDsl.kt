package core

abstract class AbstractCorDsl<T>(
    override var title: String = "",
    override var description: String = "",
    private val workers: MutableList<ICorExecDsl<T>> = mutableListOf(),
    open var blockOn: suspend T.() -> Boolean = { true },
    open var blockExcept: suspend T.(e: Throwable) -> Unit = { e: Throwable -> throw e }
) : ICorChainDsl<T> {

    override fun on(function: suspend T.() -> Boolean) {
        blockOn = function
    }

    override fun except(function: suspend T.(e: Throwable) -> Unit) {
        blockExcept = function
    }

    override fun add(worker: ICorExecDsl<T>) {
        workers.add(worker)
    }

    abstract override fun build(): ICorExec<T>
}