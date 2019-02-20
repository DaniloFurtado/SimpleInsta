package desenv.danilo.simpleinsta.util.bindable

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import desenv.danilo.simpleinsta.ui.profile.TipoList


@BindingAdapter("setAdapter")
fun androidx.recyclerview.widget.RecyclerView.setAdapter(myAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>?) {
    if (myAdapter != null)
    this.adapter = myAdapter
}


@BindingAdapter("setLayoutManager")
fun androidx.recyclerview.widget.RecyclerView.setLayoutManager(tipo: TipoList) {
    this.layoutManager = GridLayoutManager(this.context, tipo.tipo)
}