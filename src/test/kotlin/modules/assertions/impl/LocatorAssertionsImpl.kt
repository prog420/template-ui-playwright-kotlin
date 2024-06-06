package modules.assertions.impl

import com.microsoft.playwright.Locator
import com.microsoft.playwright.impl.LocatorAssertionsImpl
import io.qameta.allure.Allure
import junit.framework.AssertionFailedError


class LocatorAssertionsImpl(private val locator: Locator): LocatorAssertionsImpl(locator) {
    private var description: String? = null

    fun describedAs(description: String): modules.assertions.impl.LocatorAssertionsImpl {
        this.description = description
        return this
    }

    private fun decorateWithAllureStep(f: () -> Unit) {
        if (description != null) {
            Allure.step(description) {
                -> f()
            }
        } else {
            f()
        }
    }

    fun hasValidationMessage(message: String) {
        decorateWithAllureStep {
            this.hasJSProperty("validationMessage", message)
        }
    }

    fun isClickable(timeout: Double = 15000.0) {
        decorateWithAllureStep {
            try {
                this.locator.click(
                    Locator.ClickOptions().setTimeout(timeout).setTrial(true)
                )
            } catch (e: Exception) {
                throw AssertionFailedError("Element $locator is not clickable:\n${e.message}")
            }
        }
    }

    override fun isVisible() {
        decorateWithAllureStep { super.isVisible() }
    }
}