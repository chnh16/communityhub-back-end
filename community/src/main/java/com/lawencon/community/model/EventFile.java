package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "event_file_ck", columnNames = {"file_id", "event_id"}
)})
public class EventFile extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;
	
	@OneToOne
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	
	
	
}
