package com.jhoander.concretesolutionsgithubrepo.adapter

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jhoander.concretesolutionsgithubrepo.R
import com.jhoander.concretesolutionsgithubrepo.databinding.CustomRowBinding
import com.jhoander.concretesolutionsgithubrepo.domain.models.RepositoryData
import com.squareup.picasso.Picasso

class CustomAdapter(private var context: Context?,private var dataList: List<RepositoryData>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.custom_row,parent,false))

    }

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.initView(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(private  val binding : CustomRowBinding) : RecyclerView.ViewHolder(binding.root) {
       @SuppressLint("UseCompatLoadingForDrawables") fun initView(item : RepositoryData?){


           val metrics = context!!.resources.displayMetrics
           val width = metrics.widthPixels / 5

           val drPoster = context!!.resources.getDrawable(R.drawable.poster)
           val bitmap = (drPoster as BitmapDrawable).bitmap
           val redimPoster: Drawable = BitmapDrawable(context!!.resources, Bitmap.createScaledBitmap(bitmap, width, width, true))

           binding.namePull.text = item?.getTitle()
           binding.nameUser.text = item?.getUser()?.login
           binding.descriptionPull.text = item?.getBody()
           binding.createPull.text = item?.getCreated_at()?.replace("T","")?.replace("Z","")
           Picasso.get()
               .load(item?.getUser()?.avatar_url)
               .placeholder(redimPoster)
               .error(redimPoster)
               .resize(width, 0)
               .into(binding.photoPull)


           binding.cardView.setOnClickListener {
               val uri = Uri.parse(item?.getHtml_url())
               val likeIng = Intent(Intent.ACTION_VIEW, uri)
               try {
                   context!!.startActivity(likeIng)
               } catch (e: ActivityNotFoundException) {
                   context!!.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item?.getHtml_url())))
               }
           }
       }


    }
}