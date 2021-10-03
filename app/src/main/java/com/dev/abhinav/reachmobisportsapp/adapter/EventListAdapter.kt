package com.dev.abhinav.reachmobisportsapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.abhinav.reachmobisportsapp.model.Event
import com.dev.abhinav.reachmobisportsapp.databinding.EventListItemBinding

class EventListAdapter: RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    private var eventList = mutableListOf<Event>()

    fun setEventList(events: List<Event>) {
        this.eventList = events.toMutableList()
        Log.d("aaaAdapter", events[0].leagueName)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: EventListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("aaaAdapter", "inside on create view holder")
        val binding = EventListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("aaaAdapter", "inside on bind view holder")
        val event = eventList[position]
        Log.d("aaaAdapter", event.eventName)
        holder.binding.leagueName.text = event.leagueName
        holder.binding.eventName.text = event.eventName
        holder.binding.homeTeam.text = event.homeTeam
        holder.binding.homeScore.text = event.homeScore
        holder.binding.awayTeam.text = event.awayTeam
        holder.binding.awayScore.text = event.awayScore
        holder.binding.venue.text = event.venue
        holder.binding.date.text = event.eventDate
    }

    override fun getItemCount(): Int {
        Log.d("aaaAdapter", eventList.size.toString())
        return eventList.size
    }
}
