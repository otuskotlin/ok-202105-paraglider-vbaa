package handlers

import core.*

@CorDslMarker
fun <T> ICorChainDsl<T>.chain(function: CorChainDsl<T>.() -> Unit) {
    add(CorChainDsl<T>().apply(function))
}

class CorChain<T>(
    private val execs: List<ICorExec<T>>,
    override val title: String,
    override val description: String = "",
    override val blockOn: suspend T.() -> Boolean = { true },
    override val blockExcept: suspend T.(Throwable) -> Unit = {},
) : AbstractCor<T>(
    title, description, blockOn, blockExcept
){
    override suspend fun handle(context: T) {
        execs.forEach { it.exec(context) }
    }
}

//СorChainDsl это билдер который создает CorChain
@CorDslMarker
class CorChainDsl<T>(
    override var title: String = "",
    override var description: String = "",
    private val workers: MutableList<ICorExecDsl<T>> = mutableListOf(),
    override var blockOn: suspend T.() -> Boolean = { true },
    override var blockExcept: suspend T.(Throwable) -> Unit = { e: Throwable -> throw e }
) : AbstractCorDsl<T>(
    title, description, workers, blockOn, blockExcept
) {
    override fun build(): ICorExec<T> = CorChain<T> (
        title = title,
        description = description,
        execs = workers.map { it.build() }.toList(),
        blockOn = blockOn,
        blockExcept = blockExcept
    )
}