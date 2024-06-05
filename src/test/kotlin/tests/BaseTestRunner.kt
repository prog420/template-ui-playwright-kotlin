package tests

import modules.Env
import modules.extensions.PlaywrightExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.RegisterExtension
import ui.pages.PlaywrightPage


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseTestRunner {
    companion object {
        @JvmStatic
        @RegisterExtension
        val pw = PlaywrightExtension()
        val env = Env()
    }

    lateinit var pwPage: PlaywrightPage

    @BeforeEach
    fun initPages() {
        this.pwPage = PlaywrightPage(pw.page)
    }
}