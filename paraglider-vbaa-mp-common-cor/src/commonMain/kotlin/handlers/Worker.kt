package handlers

import core.*

@CorDslMarker
fun <T> ICorChainDsl<T>.worker(
    title: String,
    description: String = "",
    function: T.() -> Unit
) {
    add(
        CorWorkerDsl<T>(
            title = title,
            description = description,
            blockHandle = function
        )
    )
}

@CorDslMarker
fun <T> ICorChainDsl<T>.worker(function: CorWorkerDsl<T>.() -> Unit) {
    add(
        CorWorkerDsl<T>().apply(function)
    )
}

class CorWorker<T>(
    override val title: String,
    override val description: String = "",
    val blockOn: T.() -> Boolean = { true },
    val blockHandle: T.() -> Unit = {},
    val blockExcept: T.(Throwable) -> Unit = {}
) : ICorWorker<T> {
    override suspend fun on(context: T): Boolean = blockOn(context)
    override suspend fun handle(context: T) = blockHandle(context)
    override suspend fun except(context: T, e: Throwable) = blockExcept(context, e)
}

@CorDslMarker
class CorWorkerDsl<T>(
    override var title: String = "",
    override var description: String = "",
    var blockOn: T.() -> Boolean = { true },
    var blockHandle: T.() -> Unit = {},
    var blockExcept: T.(e: Throwable) -> Unit = { e: Throwable -> throw e }
) : ICorWorkerDsl<T> {

    override fun handle(function: T.() -> Unit) {
        blockHandle = function
    }

    override fun on(function: T.() -> Boolean) {
        blockOn = function
    }

    override fun except(function: T.(e: Throwable) -> Unit) {
        blockExcept = function
    }

    override fun build(): ICorExec<T> = CorWorker<T>(
        title = title,
        description = title,
        blockOn = blockOn,
        blockHandle = blockHandle,
        blockExcept = blockExcept
    )
}