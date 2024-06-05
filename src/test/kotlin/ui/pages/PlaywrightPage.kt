package ui.pages

import com.microsoft.playwright.Page
import com.microsoft.playwright.Locator
import io.qameta.allure.Step
import modules.BASE_URL


class PlaywrightPage(page: Page, ): BasePage(page) {
    override val pageURL = BASE_URL
    @PublishedApi
    internal val getStartedBtn: Locator = page.locator("a.getStarted_Sjon")
    @PublishedApi
    internal val apiBtn: Locator = page.locator("a.navbar__link[href='/docs/api/class-playwright']")

    @Step("Click 'Get Started' Button")
    fun openDocumentationIntro() {
        this.getStartedBtn.click()
    }

    @Step("Click 'API' Button")
    fun openAPIDocumentation() {
        this.apiBtn.click()
    }
}