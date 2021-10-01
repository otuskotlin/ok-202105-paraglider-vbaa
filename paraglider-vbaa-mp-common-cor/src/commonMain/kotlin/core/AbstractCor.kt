package core

abstract class AbstractCor<T>(
    override val title: String,
    override val description: String = "",
    open val blockOn: T.() -> Boolean = { true },
    open val blockExcept: T.(e: Throwable) -> Unit = {}
) : ICorWorker<T> {
    override suspend fun on(context: T): Boolean = blockOn(context)
    override suspend fun except(context: T, e: Throwable) = blockExcept(context, e)
    abstract override suspend fun handle(context: T)
}