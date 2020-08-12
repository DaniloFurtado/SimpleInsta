package desenv.danilo.simpleinsta.presentation.util.bindable

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import desenv.danilo.simpleinsta.presentation.R
import desenv.danilo.simpleinsta.presentation.util.CircleTransform

@BindingAdapter("app:setUrlImage")
fun ImageView.setUrlImage(url: String?){
    if (url != null && url.isNotBlank())
        Picasso.with(this.context)
            .load(url)
            .transform(CircleTransform())
            .error(R.drawable.ic_launcher)
            .placeholder(R.drawable.progress_animation)
            .into(this)
}

@BindingAdapter("app:setUrlImageNoRound")
fun ImageView.setUrlImageNoRound(url: String?) {
    if (url != null && url.isNotEmpty())
        Picasso.with(this.context)
            .load(url)
            .error(R.drawable.ic_launcher)
            .into(this)
}

@BindingAdapter("app:setUrlImageNoRoundToGrid")
fun ImageView.setUrlImageNoRoundToGrid(url: String?) {
    if (url != null && url.isNotEmpty())
        Picasso.with(this.context)
            .load(url)
            .fit()
            .centerCrop()
            .error(R.drawable.ic_launcher)
            .placeholder(R.drawable.progress_animation)
            .into(this)
}