package core

interface ICorExec<T> {
    suspend fun exec(context: T)
}

interface ICorWorker<T> : ICorExec<T> {
    val title: String
    val description: String
    suspend fun on(context: T): Boolean
    suspend fun handle(context: T)
    suspend fun except(context: T, e: Throwable)
    override suspend fun exec(context: T) {
        if (on(context)) {
            try {
                handle(context)
            } catch (e: Exception) {
                except(context, e)
            }
        }
    }
}