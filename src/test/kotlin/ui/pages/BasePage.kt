package ui.pages

import com.microsoft.playwright.Page
import io.qameta.allure.Step
import modules.BASE_URL
import modules.allure.AllureLifecycleManager


open class BasePage (
    private val page: Page
) {
    protected open val pageURL = BASE_URL

    @Step("Open URL: {url}")
    fun open(url: String = pageURL) {
        this.page.navigate(url)
    }

    @Step("Open URL: {url}")
    fun openHiddenAllureParameters(url: String = pageURL) {
        AllureLifecycleManager.hideStepParameters()
        this.page.navigate(url)
    }

}