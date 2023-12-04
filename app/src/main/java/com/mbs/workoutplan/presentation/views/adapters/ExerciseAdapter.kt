package com.mbs.workoutplan.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mbs.workoutplan.data.db.models.ExerciseDTO
import com.mbs.workoutplan.databinding.ExerciseItemBinding
import com.mbs.workoutplan.util.Diff
import com.mbs.workoutplan.util.load

class ExerciseAdapter(
    private val onDeleteClick: (ExerciseDTO) -> Unit
) : ListAdapter<ExerciseDTO, ExerciseAdapter.ViewHolder>(Diff<ExerciseDTO>()) {

    inner class ViewHolder(private val binding: ExerciseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExerciseDTO) {
            with(binding) {
                item.name?.let { title.text = it }
                item.obs?.let { obs.text = it }
                delete.setOnClickListener { onDeleteClick(item) }
                item.image?.let { img.load(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ExerciseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}