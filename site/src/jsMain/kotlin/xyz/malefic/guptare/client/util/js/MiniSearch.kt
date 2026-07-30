package xyz.malefic.guptare.client.util.js

external interface BlogDocument {
    var id: String
    var title: String
    var summary: String
    var content: String
    var tags: Array<String>
    var imageUrl: String?
    var date: String
}

external interface BoostFields {
    var title: Int
    var summary: Int
    var tags: Int
    var content: Int
}

external interface SearchOptions {
    var boost: BoostFields
    var fuzzy: Double
    var prefix: Boolean
}

external interface SearchResult {
    val id: String
    val score: Double
    val title: String
    val summary: String
    val imageUrl: String?
    val tags: Array<String>
    val date: String
}

external interface MiniSearchOptions {
    var fields: Array<String>
    var storeFields: Array<String>
    var idField: String
    var extractField: (doc: BlogDocument, field: String) -> dynamic
}

@JsModule("minisearch")
@JsNonModule
external class MiniSearch(
    options: MiniSearchOptions,
) {
    fun add(document: BlogDocument)

    fun addAll(documents: Array<BlogDocument>)

    fun search(
        query: String,
        searchOptions: SearchOptions = definedExternally,
    ): Array<SearchResult>
}
