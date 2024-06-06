package tests

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import modules.assertions.Assertions.Companion.assertThat
import org.junit.jupiter.api.assertAll


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
        assertAll(
            { assertThat(pwPage.someBtn).describedAs("Custom isVisible() Description").isVisible() },
            { assertThat(pwPage.apiBtn).describedAs("Custom isClickable() Description").isClickable() },
            { assertThat(pwPage.someBtn).isClickable() }
        )
    }

    @Test
    @DisplayName("Test Two")
    fun testTwoFail() {
        val expectedTitle = "Installation | Test Two"
        pwPage.open()
        pwPage.openDocumentationIntro()
        assertThat(pwPage.getStartedBtn).hasValidationMessage("Some message")
        assertThat(pw.page).hasTitle(expectedTitle)
    }

    @Test
    @DisplayName("Test Three")
    fun testThreeFail() {
        pwPage.open()
        pwPage.openAPIDocumentation()
        assertThat(pw.page).hasTitle("Playwright Library | Test Three")
    }
}
