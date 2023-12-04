package com.example.retrofitsamplewithcleanarchitecture.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder constructor(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root)