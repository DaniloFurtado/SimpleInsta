package desenv.danilo.simpleinsta.util.bindable

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import desenv.danilo.simpleinsta.R
import desenv.danilo.simpleinsta.util.CircleTransform

@BindingAdapter("setUrlImage")
fun ImageView.setUrlImage(url: String?){
    if (url != null)
        Picasso.with(this.context)
            .load(url)
            .transform(CircleTransform())
            .error(R.mipmap.ic_launcher)
            .placeholder(R.drawable.progress_animation)
            .into(this)
}
@BindingAdapter("setUrlImageNoRound")
fun ImageView.setUrlImageNoRound(url: String?) {
    if (url != null)
        Picasso.with(this.context)
            .load(url)
            .error(R.mipmap.ic_launcher)
            .into(this)
}

@BindingAdapter("setUrlImageNoRoundToGrid")
fun ImageView.setUrlImageNoRoundToGrid(url: String?) {
    if (url != null)
        Picasso.with(this.context)
            .load(url)
            .fit()
            .centerCrop()
            .error(R.mipmap.ic_launcher)
            .placeholder(R.drawable.progress_animation)
            .into(this)
}