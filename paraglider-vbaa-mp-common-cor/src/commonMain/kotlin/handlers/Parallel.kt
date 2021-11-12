package handlers

import core.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

@CorDslMarker
fun <T> ICorChainDsl<T>.parallel(function: CorParallelDsl<T>.() -> Unit) {
    add(CorParallelDsl<T>().apply(function))
}

class CorParallel<T>(
    private val execs: List<ICorExec<T>>,
    override val title: String,
    override val description: String = "",
    override val blockOn: suspend T.() -> Boolean = { true },
    override val blockExcept: suspend T.(Throwable) -> Unit = {},
) : AbstractCor<T>(
    title, description, blockOn, blockExcept
) {
    override suspend fun handle(context: T) = coroutineScope {
        execs.map {
            launch {
                it.exec(context)
            }
        }.joinAll()
    }
}

@CorDslMarker
class CorParallelDsl<T>(
    override var title: String = "",
    override var description: String = "",
    private val workers: MutableList<ICorExecDsl<T>> = mutableListOf(),
    override var blockOn: suspend T.() -> Boolean = { true },
    override var blockExcept: suspend T.(Throwable) -> Unit = { e: Throwable -> throw e }
) : AbstractCorDsl<T>(
    title, description, workers, blockOn, blockExcept
) {
    override fun build(): ICorExec<T> = CorParallel<T> (
        title = title,
        description = description,
        execs = workers.map { it.build() }.toList(),
        blockOn = blockOn,
        blockExcept = blockExcept
    )
}