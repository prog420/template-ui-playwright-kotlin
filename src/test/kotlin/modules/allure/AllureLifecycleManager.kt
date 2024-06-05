package modules.allure

import com.microsoft.playwright.Page
import com.microsoft.playwright.Tracing
import com.microsoft.playwright.Video
import io.qameta.allure.Allure
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Path


class AllureLifecycleManager {
    companion object {
        private val allureLifecycle = Allure.getLifecycle()

        /**
         * Remove parameters for test case steps in the Allure report.
         * Use Case: sensitive data (e.g. passwords)
         */
        @JvmStatic
        fun hideStepParameters() {
            allureLifecycle.updateStep {
                it.parameters = listOf()
            }
        }

        @JvmStatic
        fun attach(
            video: Video, name: String = "video", type: String = "video/webm", fileExtension: String = ".webm"
        ) {
            val videoFile = File(video.path().toUri())
            val videoInputStream: InputStream = FileInputStream(videoFile)
            allureLifecycle.addAttachment(name, type, fileExtension, videoInputStream)
        }

        @JvmStatic
        fun attach(
            screenshot: ByteArray, name: String = "image", type: String = "image/png", fileExtension: String = "png"
        ) {
            allureLifecycle.addAttachment(name, type, fileExtension, screenshot)
        }

        @JvmStatic
        fun attach(
            tracePath: Path, name: String = "trace", type: String = "application/zip", fileExtension: String = "zip"
        ) {
            val traceFile = File(tracePath.toUri())
            val traceInputStream: InputStream = FileInputStream(traceFile)
            allureLifecycle.addAttachment(name, type, fileExtension, traceInputStream)
        }

    }
}
