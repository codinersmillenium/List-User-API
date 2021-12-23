package com.example.submission2.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.Model.User
import com.example.submission2.R


class UserAdapter (private val listUser: ArrayList<User>?):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var clickListener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_user, parent, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = listUser?.get(position)
        data?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return listUser?.size ?: 0
    }

    fun setOnItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    inner class UserViewHolder (view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{

        var tvName: TextView = view.findViewById(R.id.tv_name)
        var tvUsername: TextView = view.findViewById(R.id.tv_username)
        var img_photo : ImageView = view.findViewById(R.id.img_item_photo)

        init {
            if (clickListener != null) {
                itemView.setOnClickListener(this)
            }
        }

        fun bind(user: User){
            tvName.text = user.name
            tvUsername.text = user.username
            Glide.with(itemView.context).load(user.photo)
                .apply(RequestOptions().override(250,250))
                .into(img_photo)
        }

        override fun onClick(view: View?) {
            if (view != null) {
                clickListener?.onItemClick(view,adapterPosition)
            }
        }
    }

    interface ClickListener {
        fun onItemClick(view: View,position: Int)
    }


}

