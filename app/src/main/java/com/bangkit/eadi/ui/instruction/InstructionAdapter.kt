package com.bangkit.eadi.ui.instruction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.eadi.R

class InstructionAdapter(private val instructions: List<String>) : RecyclerView.Adapter<InstructionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_instruction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val instruction = instructions[position]
        holder.bind(instruction, position)
    }

    override fun getItemCount(): Int {
        return instructions.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvInstructionNumber: TextView = itemView.findViewById(R.id.tv_instruction_number)
        private val tvInstruction: TextView = itemView.findViewById(R.id.tv_instruction)

        fun bind(instruction: String, position: Int) {
            tvInstructionNumber.text = "${position + 1}."
            tvInstruction.text = instruction
        }
    }
}
