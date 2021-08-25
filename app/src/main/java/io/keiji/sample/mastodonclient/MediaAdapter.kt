package io.keiji.sample.mastodonclient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.keiji.sample.mastodonclient.databinding.ListItemTootBinding

class MediaListAdapter (
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<MediaListAdapter.ViewHolder>() {

    var mediaList: List<Media> = emptyList()
      set(value) {
          field = value
          notifyDataSetChanged()
      }
}