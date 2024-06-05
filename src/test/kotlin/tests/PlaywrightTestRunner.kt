package tests

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import modules.assertions.Assertions.Companion.assertThat


@Epic("System")
@Feature("Web Interface")
@Story("Module")
@Execution(ExecutionMode.SAME_THREAD)
class PlaywrightTestRunner: BaseTestRunner() {

    @Test
    @DisplayName("Test One")
    fun testOneSuccess() {
        pwPage.open()
        pwPage.openDocumentationIntro()
        assertThat(pw.page).hasTitle("Installation | Playwright")
    }

    @Test
    @DisplayName("Test Two")
    fun testTwoFail() {
        pwPage.open()
        pwPage.openDocumentationIntro()
        assertThat(pw.page).hasTitle("Installation | Test Two")
    }

    @Test
    @DisplayName("Test Three")
    fun testThreeFail() {
        pwPage.open()
        pwPage.openAPIDocumentation()
        assertThat(pw.page).hasTitle("Playwright Library | Test Three")
    }
}