package com.dev.abhinav.reachmobisportsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.abhinav.reachmobisportsapp.model.Event
import com.dev.abhinav.reachmobisportsapp.databinding.EventListItemBinding

class EventListAdapter: RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    private var eventList = mutableListOf<Event>()

    fun setEventList(events: List<Event>) {
        this.eventList = events.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: EventListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]
        holder.binding.leagueName.text = "${event.leagueName} (${event.season})"
        holder.binding.eventName.text = event.eventName
        holder.binding.homeTeam.text = event.homeTeam
        holder.binding.homeScore.text = event.homeScore
        holder.binding.awayTeam.text = event.awayTeam
        holder.binding.awayScore.text = event.awayScore
        holder.binding.venue.text = event.venue
        holder.binding.date.text = event.eventDate
        Glide.with(holder.binding.homeTeamBadge.context).load(event.homeBadge).into(holder.binding.homeTeamBadge)
        Glide.with(holder.binding.awayTeamBadge.context).load(event.awayBadge).into(holder.binding.awayTeamBadge)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}