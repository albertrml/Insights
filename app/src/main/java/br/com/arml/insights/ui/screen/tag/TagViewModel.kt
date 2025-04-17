package br.com.arml.insights.ui.screen.tag

import androidx.lifecycle.viewModelScope
import br.com.arml.insights.domain.TagUiUseCase
import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.ui.screen.common.BaseViewModel
import br.com.arml.insights.utils.data.Response
import br.com.arml.insights.utils.data.SortedTag
import br.com.arml.insights.utils.data.update
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
            is TagEvent.OnSearch -> {
                searchTagUi(event.query)
            }
            is TagEvent.OnFetchAllItems -> { retrieveTagUi(SortedTag.ByNameAscending) }
            else -> sendEventForEffect(event)
        }
    }

    private fun insertTagUi() {
        viewModelScope.launch {
            val newTagUi = state.value.selectedTagUi

            if (newTagUi == null){
                sendEffect(TagEffect.ShowSnackBar(TagIsNullException().message))
                _state.update { state ->
                    state.copy(operationState = Response.Failure(TagIsNullException()))
                }
                return@launch
            }

            val (isTagUiValid, invalidException) = TagUi.isValid(newTagUi)
            if (!isTagUiValid){
                sendEffect(TagEffect.ShowSnackBar(invalidException?.message!!))
                _state.update { state ->
                    state.copy(operationState = Response.Failure(invalidException))
                }
                return@launch
            }

            if (tagUiUseCase.isTagNameExists(newTagUi.name)) {
                sendEffect(TagEffect.ShowSnackBar(TagAlreadyExistsException().message))
                _state.update { state ->
                    state.copy(operationState = Response.Failure(TagAlreadyExistsException()))
                }
                return@launch
            }


            tagUiUseCase.insertTagUi(newTagUi).collect { response ->
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

    private fun updateTagUi(){
        viewModelScope.launch {
            val updatedTagUi = state.value.selectedTagUi

            if (updatedTagUi == null){
                sendEffect(TagEffect.ShowSnackBar(TagIsNullException().message))
                _state.update { state ->
                    state.copy(operationState = Response.Failure(TagIsNullException()))
                }
                return@launch
            }

            val (isTagUiValid, invalidException) = TagUi.isValid(updatedTagUi)
            if (!isTagUiValid){
                sendEffect(TagEffect.ShowSnackBar(invalidException?.message!!))
                _state.update { state ->
                    state.copy(operationState = Response.Failure(invalidException))
                }
                return@launch
            }

            if (tagUiUseCase.isTagNameExists(updatedTagUi.name)) {
                sendEffect(TagEffect.ShowSnackBar(TagAlreadyExistsException().message))
                _state.update { state ->
                    state.copy(operationState = Response.Failure(TagAlreadyExistsException()))
                }
                return@launch
            }

            tagUiUseCase.updateTagUi(updatedTagUi).collect { response ->
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

    private fun retrieveTagUi(sortBy: SortedTag) {
        viewModelScope.launch {
            tagUiUseCase.fetchTagUi(sortBy).collect { response ->
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

    /*private fun deleteTagUi(tagUi: TagUi) {
        viewModelScope.launch {
            tagUiUseCase.deleteTagUi(tagUi).collect { response ->
                response.update(_uiState) { state, res ->
                    state.copy(deleteState = res)
                }
            }
        }
    }

    private fun editTagUi(tagUi: TagUi) {
        viewModelScope.launch {
            tagUiUseCase.updateTagUi(tagUi).collect { response ->
                response.update(_uiState) { state, res ->
                    state.copy(editState = res)
                }
            }
        }
    }*/

    /*

    */
}