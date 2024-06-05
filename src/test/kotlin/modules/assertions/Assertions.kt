package modules.assertions

import com.microsoft.playwright.APIResponse
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.microsoft.playwright.assertions.PlaywrightAssertions
import modules.assertions.impl.APIResponseAssertionsImpl
import modules.assertions.impl.LocatorAssertionsImpl
import modules.assertions.impl.PageAssertionsImpl


/**
 * Entrypoint for Locator / Page / API assertions.
 */
interface Assertions: PlaywrightAssertions {
    companion object {
        fun assertThat(locator: Locator): LocatorAssertionsImpl {
            return LocatorAssertionsImpl(locator)
        }

        fun assertThat(response: APIResponse): APIResponseAssertionsImpl {
            return APIResponseAssertionsImpl(response)
        }

        fun assertThat(page: Page): PageAssertionsImpl {
            return PageAssertionsImpl(page)
        }
    }
}