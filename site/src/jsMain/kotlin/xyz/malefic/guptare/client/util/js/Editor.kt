package xyz.malefic.guptare.client.util.js

@JsModule("@toast-ui/editor")
@JsNonModule
external class Editor(
    options: dynamic,
) {
    fun getMarkdown(): String

    fun setMarkdown(markdown: String)

    fun destroy()

    companion object {
        fun factory(options: dynamic): dynamic
    }
}
