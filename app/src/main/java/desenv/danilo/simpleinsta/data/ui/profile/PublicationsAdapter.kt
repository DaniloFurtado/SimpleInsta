package desenv.danilo.simpleinsta.data.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import desenv.danilo.simpleinsta.data.data.models.DataMedias
import desenv.danilo.simpleinsta.databinding.ItemPostGridBinding
import desenv.danilo.simpleinsta.databinding.ItemPostListBinding

class PublicationsAdapter(var posts: Array<DataMedias>,
                          var typeList: TipoList): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var atachedRecycler = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater =  LayoutInflater.from(parent.context)
        return if (TipoList.LIST == typeList){
            MyHolderList(ItemPostListBinding.inflate(layoutInflater, parent, false))
        }else{
            MyHolderGrid(ItemPostGridBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = posts[position]
        if (holder is MyHolderGrid){
            holder.bind(item)
        }else if(holder is MyHolderList) {
            holder.bind(item)
        }
    }


    inner class  MyHolderList(private var binding: ItemPostListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(media: DataMedias){
            binding.setVariable(BR.dataMedia, media)
            binding.executePendingBindings()
        }
    }

    inner class  MyHolderGrid(private var binding: ItemPostGridBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(media: DataMedias){
            binding.setVariable(BR.dataMediaGrid, media)
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return typeList.tipo
    }

    fun notifyChanged(){
        if (atachedRecycler)
            try {
                notifyDataSetChanged()
            }catch (e: Exception){}

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        atachedRecycler = true
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        atachedRecycler = false
    }
}

enum class TipoList(var tipo: Int){
    GRID(3), LIST(1)
}