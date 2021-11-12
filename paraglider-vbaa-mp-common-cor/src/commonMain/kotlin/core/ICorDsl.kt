package core

import handlers.CorChainDsl

fun <T> chain(function: CorChainDsl<T>.() -> Unit) = CorChainDsl<T>().apply(function)

interface ICorChainDsl<T> : ICorExecDsl<T>, ICorHandlerDsl<T> {
    fun add(worker: ICorExecDsl<T>)
}

interface ICorWorkerDsl<T> : ICorExecDsl<T>, ICorHandlerDsl<T> {
    fun handle(function: suspend T.() -> Unit)
}

interface ICorExecDsl<T> {
    var title: String
    var description: String
    fun build(): ICorExec<T>
}

interface ICorHandlerDsl<T> {
    fun on(function: suspend T.() -> Boolean)
    fun except(function: suspend T.(e: Throwable) -> Unit)
}