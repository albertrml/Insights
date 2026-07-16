package br.com.arml.insights.ui.screen.tag

import androidx.lifecycle.viewModelScope
import br.com.arml.core.response.Response
import br.com.arml.core.response.update
import br.com.arml.insights.domain.TagUiUseCase
import br.com.arml.insights.model.domain.TagUi
import br.com.arml.insights.ui.screen.common.BaseViewModel
import br.com.arml.insights.utils.data.SortedTag
import br.com.arml.insights.utils.exception.InsightException.TagAlreadyExistsException
import br.com.arml.insights.utils.exception.TagException.TagIsNullException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val tagUiUseCase: TagUiUseCase,
    reducer: TagReducer,
) : BaseViewModel<TagState, TagEvent, TagEffect>(
    initialState = TagState(),
    reducer = reducer,
) {
    init {
        retrieveTagUi(SortedTag.ByNameAscending)
    }

    fun onEvent(event: TagEvent) {
        when (event) {
            is TagEvent.OnInsertOrUpdate -> {
                when(event.operation){
                    is TagOperation.OnInsert -> insertTagUi()
                    is TagOperation.OnUpdate -> updateTagUi()
                    else -> {}
                }
            }
            is TagEvent.OnSearch -> { searchTagUi(event.query) }
            is TagEvent.OnFetchAllItems -> { retrieveTagUi(SortedTag.ByNameAscending) }
            is TagEvent.OnDelete -> { deleteTagUi() }
            else -> sendEventForEffect(event)
        }
    }

    private fun insertTagUi() {
        viewModelScope.launch {
            val newTagUi = state.value.selectedTagUi

            /*
                Isto deve ser um flow de validação que alimentará o state do botão de salvar.
                Sua inicialização ocorrerá no init do viewmodel.
             */

            try {
                TagUi.isValid(newTagUi)
            } catch (e: Exception) {
                sendEffect(TagEffect.ShowSnackBar(e.message!!))
                _state.update { state ->
                    state.copy(operationState = Response.Failure(e))
                }
                return@launch
            }

            newTagUi?.let { tag ->
                if (tagUiUseCase.isTagNameExists( tag.name)) {
                    sendEffect(TagEffect.ShowSnackBar(TagAlreadyExistsException().message))
                    _state.update { state ->
                        state.copy(operationState = Response.Failure(TagAlreadyExistsException()))
                    }
                    return@launch
                }

                tagUiUseCase.insertTagUi(tag).collect { response ->
                    response.update(_state) { state, res ->
                        when(res){
                            is Response.Loading -> { state.copy(operationState = res) }
                            is Response.Success -> {
                                sendEffect(TagEffect.OnHideBottomSheet)
                                state.copy(
                                    selectedTagUi = null,
                                    selectedOperation = TagOperation.None,
                                    operationState = res
                                )
                            }
                            is Response.Failure -> {
                                val failureMsg = res.exception.message?:"Something went wrong"
                                sendEffect(TagEffect.ShowSnackBar(failureMsg))
                                state.copy(operationState = res)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateTagUi(){
        viewModelScope.launch {
            val updatedTagUi = state.value.selectedTagUi

            updatedTagUi?.let { tag ->
                try {
                    TagUi.isValid(tag)
                } catch (e: Exception) {
                    sendEffect(TagEffect.ShowSnackBar(e.message!!))
                    _state.update { state ->
                        state.copy(operationState = Response.Failure(e))
                    }
                    return@launch
                }

                tagUiUseCase.updateTagUi(tag).collect { response ->
                    response.update(_state) { state, res ->
                        when(res){
                            is Response.Loading -> {}
                            is Response.Success -> {
                                sendEffect(TagEffect.OnHideBottomSheet)
                            }
                            is Response.Failure -> {
                                val failureMsg = res.exception.message?:"Something went wrong"
                                sendEffect(TagEffect.ShowSnackBar(failureMsg))
                            }
                        }
                        state.copy(operationState = res)
                    }
                }
            }
        }
    }

    private fun retrieveTagUi(sortBy: SortedTag) {
        viewModelScope.launch {
            tagUiUseCase.fetchTagUi(sortBy).collect { response ->
                if(response is Response.Failure)
                    sendEffect(
                        TagEffect.ShowSnackBar(
                            response.exception.message?:"Something went wrong"
                        )
                    )
                response.update(_state) { state, res ->
                    state.copy(tags = res)
                }
            }
        }
    }

    private fun searchTagUi(query: String) {
        viewModelScope.launch {
            tagUiUseCase.searchTagByName(query).collect { response ->
                response.update(_state) { state, res ->
                    state.copy(tags = res)
                }
            }
        }
    }

    private fun deleteTagUi() {
        viewModelScope.launch {
            val tagUiForDelete = state.value.selectedTagUi

            if (tagUiForDelete == null){
                sendEffect(TagEffect.ShowSnackBar(TagIsNullException().message))
                _state.update { state ->
                    state.copy(operationState = Response.Failure(TagIsNullException()))
                }
                return@launch
            }

            tagUiUseCase.deleteTagUi(tagUiForDelete).collect { response ->
                response.update(_state) { state, res ->
                    when(res){
                        is Response.Loading -> {
                            state.copy(operationState = res)
                        }
                        is Response.Success -> {
                            sendEffect(TagEffect.OnHideDeleteDialog)
                            state.copy(
                                selectedTagUi = null,
                                operationState = res
                            )
                        }
                        is Response.Failure -> {
                            val failureMsg = res.exception.message?:"Something went wrong"
                            sendEffect(TagEffect.ShowSnackBar(failureMsg))
                            state.copy(operationState = res)
                        }
                    }
                }
            }
        }
    }
}