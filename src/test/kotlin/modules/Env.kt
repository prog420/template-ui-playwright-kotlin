package modules

import io.github.cdimascio.dotenv.dotenv


val dotenv = dotenv {
    ignoreIfMissing = true
}

data class Env(
    val login: String = dotenv["LOGIN"] ?: ""
)