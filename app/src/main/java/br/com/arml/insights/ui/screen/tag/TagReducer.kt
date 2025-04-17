package br.com.arml.insights.ui.screen.tag

import br.com.arml.insights.model.entity.TagUi
import br.com.arml.insights.ui.screen.common.Reducer
import br.com.arml.insights.utils.data.Response
import javax.inject.Inject

class TagReducer @Inject constructor() : Reducer<TagState, TagEvent, TagEffect> {
    override fun reduce(
        previousState: TagState,
        event: TagEvent
    ): Pair<TagState, TagEffect?> {
        return when(event){
            /* Actions to handle backend events */
            is TagEvent.OnInsertOrUpdate -> previousState to null
            is TagEvent.OnSearch -> previousState to null
            is TagEvent.OnFetchAllItems -> previousState to null
            is TagEvent.OnDelete -> previousState to null

            /* Actions to handle frontend events */
            is TagEvent.OnEditName -> {
                val updatedState = previousState.selectedTagUi?.copy(name = event.name)
                previousState.copy(selectedTagUi = updatedState) to null
            }

            is TagEvent.OnEditDescription -> {
                val updatedState = previousState.selectedTagUi?.copy(description = event.description)
                previousState.copy(selectedTagUi = updatedState) to null
            }

            is TagEvent.OnEditColor -> {
                val updatedState = previousState.selectedTagUi?.copy(
                    color = event.color
                )
                previousState.copy(selectedTagUi = updatedState) to null
            }

            is TagEvent.OnClickToOpenSheet -> {
                val selectedTagUi = event.selectedTagUi?: TagUi.fromTag(null)
                previousState.copy(
                    selectedTagUi = selectedTagUi,
                    selectedOperation = event.tagOperation,
                    operationState = Response.Loading
                ) to TagEffect.OnShowContentSheet
            }

            is TagEvent.OnClickToCloseSheet -> {
                previousState.copy(
                    selectedTagUi = null,
                    selectedOperation = TagOperation.None
                ) to TagEffect.OnHideBottomSheet
            }

            is TagEvent.OnClickToShowDeleteDialog -> {
                previousState.copy(
                    selectedTagUi = event.selectedTag.copy()
                ) to TagEffect.OnShowDeleteDialog
            }

            is TagEvent.OnClickToCloseDeleteDialog -> {
                previousState.copy(
                    selectedTagUi = null
                ) to TagEffect.OnHideDeleteDialog
            }

            is TagEvent.OnSortTagsByNameAscending -> {
                val newState =  if (previousState.tags is Response.Success){
                    previousState.copy(
                        tags = Response.Success(
                            previousState.tags.result.sortedBy { it.name }
                        )
                    )
                } else {
                    previousState
                }
                newState to null
            }

            is TagEvent.OnSortTagsByNameDescending -> {
                val newState =  if (previousState.tags is Response.Success){
                    previousState.copy(
                        tags = Response.Success(
                            previousState.tags.result.sortedByDescending { it.name }
                        )
                    )
                } else {
                    previousState
                }
                newState to null
            }
        }
    }
}