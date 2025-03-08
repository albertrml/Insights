package br.com.arml.insights.utils.exception

sealed class InsightException(override val message: String): Exception() {
    class TitleException: InsightException( "Insight title cannot be empty" )
    class BodyException: InsightException( "Insight body cannot be empty" )
    class NameTagException: InsightException( "Tag name cannot be empty" )
    class TagAlreadyExistsException: InsightException( "Tag Name already exists" )
    class TagNotFoundException: InsightException( "Tag not found" )
    class NoteNotFoundException: InsightException( "Note not found" )
}