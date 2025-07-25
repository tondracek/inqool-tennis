package cz.tondracek.inqooltennis.core.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
class BadRequestException(message: String = "Bad request") : RuntimeException(message)

@ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
class UnauthorizedException(message: String = "Unauthorized") : RuntimeException(message)

@ResponseStatus(HttpStatus.FORBIDDEN) // 403
class ForbiddenException(message: String = "Forbidden") : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND) // 404
class NotFoundException(message: String = "Resource not found") : RuntimeException(message)

@ResponseStatus(HttpStatus.CONFLICT) // 409
class ConflictException(message: String = "Conflict") : RuntimeException(message)

