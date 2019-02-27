package desenv.danilo.simpleinsta.presentation.util.bindable

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import desenv.danilo.simpleinsta.presentation.modelbind.TipoList


@BindingAdapter("app:setAdapter")
fun androidx.recyclerview.widget.RecyclerView.setAdapter(myAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>?) {
    if (myAdapter != null)
    this.adapter = myAdapter
}


@BindingAdapter("app:setLayoutManager")
fun androidx.recyclerview.widget.RecyclerView.setLayoutManager(tipo: TipoList?) {
    if (tipo != null)
        this.layoutManager = GridLayoutManager(this.context, tipo.tipo)
}