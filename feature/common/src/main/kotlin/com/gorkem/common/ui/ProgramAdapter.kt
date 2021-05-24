/*
 * Copyright 2021 Görkem Karadoğan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gorkem.common.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gorkem.common.databinding.ItemProgramRowBinding

class ProgramAdapter(val imageUrl: String) :
    RecyclerView.Adapter<ProgramAdapter.ProgramViewModel>() {

    private var itemList: List<ProgramUIModel> = mutableListOf()

    class ProgramViewModel(
        private val imageUrl: String,
        private val binding: ItemProgramRowBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProgramUIModel) {
            with(binding) {
                tvRate.text = item.voteAverage.toString()
                tvName.text = item.title
                tvDate.text = item.releaseDate
                ivPoster.load("${imageUrl}${item.posterPath}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewModel =
        ProgramViewModel(
            imageUrl,
            ItemProgramRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ProgramViewModel, position: Int) =
        holder.bind(itemList[position])

    fun addNewList(newList: List<ProgramUIModel>) {
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(ProgramDiffUtil(this.itemList, newList))
        diffResult.dispatchUpdatesTo(this)

        itemList = newList
    }
}
