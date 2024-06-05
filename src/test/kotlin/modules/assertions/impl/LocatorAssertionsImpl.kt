package modules.assertions.impl

import com.microsoft.playwright.Locator
import com.microsoft.playwright.impl.LocatorAssertionsImpl
import junit.framework.AssertionFailedError


class LocatorAssertionsImpl(private val locator: Locator): LocatorAssertionsImpl(locator) {

    fun hasValidationMessage(message: String) {
        this.hasJSProperty("validationMessage", message)
    }

    fun isClickable(timeout: Double = 15000.0) {
        try {
            this.locator.click(
                Locator.ClickOptions().setTimeout(timeout).setTrial(true)
            )
        } catch (e: Exception) {
            throw AssertionFailedError("Element $locator is not clickable:\n${e.message}")
        }
    }
}