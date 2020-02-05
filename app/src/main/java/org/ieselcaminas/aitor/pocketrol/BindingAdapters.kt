package org.ieselcaminas.aitor.pocketrol

import android.graphics.drawable.Drawable
import de.hdodenhof.circleimageview.CircleImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("chrImage")
fun setCharacterCircleImage(imgCircle: CircleImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgCircle.context)
            .load(imgUri)
            .apply(RequestOptions()
                .error(Drawable.createFromPath("https://recap-project.eu/wp-content/uploads/2017/02/default-user.jpg"))
            )
            .into(imgCircle)
    }
}