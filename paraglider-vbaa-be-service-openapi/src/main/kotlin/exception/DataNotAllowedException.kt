package exception

import ru.kotlin.paraglider.vbaa.openapi.models.BaseMessage

class DataNotAllowedException(msg: String, request: BaseMessage) : Throwable("$msg: $request")