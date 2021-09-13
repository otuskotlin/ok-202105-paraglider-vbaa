package core

import handlers.CorChainDsl

fun <T> chain(function: CorChainDsl<T>.() -> Unit) = CorChainDsl<T>().apply(function)

interface ICorChainDsl<T> : ICorExecDsl<T>, ICorHandlerDsl<T> {
    fun add(worker: ICorExecDsl<T>)
}

interface ICorWorkerDsl<T> : ICorExecDsl<T>, ICorHandlerDsl<T> {
    fun handle(function: T.() -> Unit)
}

interface ICorExecDsl<T> {
    var title: String
    var description: String
    fun build(): ICorExec<T>
}

interface ICorHandlerDsl<T> {
    fun on(function: T.() -> Boolean)
    fun except(function: T.(e: Throwable) -> Unit)
}