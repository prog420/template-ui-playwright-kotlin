package modules.extensions

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserContext
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import com.microsoft.playwright.Tracing
import io.qameta.allure.Allure
import io.qameta.allure.AllureLifecycle
import modules.allure.AllureLifecycleManager
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Paths


class PlaywrightExtension: BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {
    private lateinit var playwright: Playwright
    private lateinit var browser: Browser
    private lateinit var context: BrowserContext
    lateinit var page: Page

    private val videoPath = Paths.get("videos/")
    private val tracePath = Paths.get("trace.zip")

    private val chromiumArgs = listOf(
        "--no-sandbox",
        "--ignore-certificate-errors",
    )

    override fun beforeAll(context: ExtensionContext?) {
        this.playwright = Playwright.create()
        this.browser = playwright.chromium().launch(
            BrowserType.LaunchOptions()
                .setArgs(chromiumArgs)
                .setHeadless(true)
//                .setSlowMo(100.0)
        )
    }

    override fun beforeEach(context: ExtensionContext?) {
        val contextOptions = Browser.NewContextOptions()
            .setRecordVideoDir(videoPath)
        this.context = browser.newContext(contextOptions)
        this.page = this.context.newPage()
        if (System.getProperty("trace") != null) {
            this.context.tracing().start(
                Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true)
            )
        }
    }

    override fun afterEach(context: ExtensionContext?) {
        /** On test fail: Attach screenshot, close page, attach video.
         * If the video will be attached to Allure report before the page is closed, video size will be 0 bytes.
         */
        if (System.getProperty("trace") != null) {
            this.context.tracing().stop(
                Tracing.StopOptions()
                    .setPath(tracePath)
            )
            AllureLifecycleManager.attach(tracePath)
        }
        val testFailed = context?.executionException?.isPresent
        if (testFailed == true) {
            AllureLifecycleManager.attach(page.screenshot())
            this.page.close()
            this.context.close()
            AllureLifecycleManager.attach(page.video())
        } else {
            this.page.close()
            this.context.close()
        }
        page.video().delete()
    }

    override fun afterAll(context: ExtensionContext?) {
        this.browser.close()
    }
}