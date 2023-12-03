package com.mbs.workoutplan.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mbs.workoutplan.data.db.models.Workout
import com.mbs.workoutplan.databinding.WorkoutItemBinding
import com.mbs.workoutplan.util.Diff
import java.text.SimpleDateFormat
import java.util.Locale

class WorkoutAdapter(
    private val onClick: (Workout) -> Unit
) : ListAdapter<Workout, WorkoutAdapter.ViewHolder>(Diff<Workout>()) {

    inner class ViewHolder(private val binding: WorkoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Workout) {
            with(binding) {
                title.text = item.name.toString()
                description.text = item.description
                numExercises.text = if (item.exercises?.size != null) {
                    (item.exercises.size + 1).toString()
                } else "0"
                date.text = item.date?.toDate()
                    ?.let { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it) }
                root.setOnClickListener { onClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WorkoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}