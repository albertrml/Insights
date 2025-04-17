package br.com.arml.insights.model.mock

import br.com.arml.insights.model.entity.NoteUi
import java.util.Date

fun createSampleNotes(): List<NoteUi> {
    val now = Date()
    return listOf(
        NoteUi(
            title = "Reunião com o Cliente",
            situation = "Discussão de Requisitos",
            body = "Reunião para discutir os requisitos do projeto X. Focar em funcionalidades principais e prazos.",
            tagId = 1,
            creationDate = now
        ),
        NoteUi(
            title = "Ideias para Novo App",
            situation = "Brainstorming",
            body = "Pensar em ideias criativas para um novo app mobile. Considerar integração com redes sociais.",
            tagId = 1,
            creationDate = Date(now.time - 86400000) // 1 dia atrás
        ),
        NoteUi(
            title = "Estudar Kotlin",
            situation = "Desenvolvimento",
            body = "Avançar nos estudos de Kotlin para Android. Praticar com Jetpack Compose e Room.",
            tagId = 1,
            creationDate = Date(now.time - 172800000) // 2 dias atrás
        ),
        NoteUi(
            title = "Planejar Viagem",
            situation = "Lazer",
            body = "Começar a planejar a próxima viagem. Escolher o destino, pesquisar hotéis e atrações.",
            tagId = 1,
            creationDate = Date(now.time - 259200000) // 3 dias atrás
        ),
        NoteUi(
            title = "Revisar Código",
            situation = "Melhorias",
            body = "Revisar o código da última sprint. Buscar por melhorias de performance e refatorar.",
            tagId = 1,
            creationDate = Date(now.time - 345600000) // 4 dias atrás
        )
    )
}