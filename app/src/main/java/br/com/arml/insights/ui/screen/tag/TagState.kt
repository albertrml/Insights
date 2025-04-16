package br.com.arml.insights.ui.screen.tag

import androidx.compose.runtime.Immutable
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.ui.screen.common.Reducer
import br.com.arml.insights.ui.screen.tag.TagOperation.None
import br.com.arml.insights.utils.data.Response

@Immutable
data class TagState(
    // Estado da busca e inserção de tags.
    val tags: Response<List<TagUi>> = Response.Loading,

    //Estado da operacao de insercao, alteracao e exclusao.
    val operationState: Response<Unit> = Response.Success(Unit),
    val selectedOperation: TagOperation = None,

    // TagUi selecionada (para edição/visualização)
    val selectedTagUi: TagUi? = null,

) : Reducer.ViewState