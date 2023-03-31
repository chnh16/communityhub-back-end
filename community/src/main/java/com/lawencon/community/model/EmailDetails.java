package com.lawencon.community.model;

public class EmailDetails {

	private String sender;
	private String recipient;
	private String msgBody;
	private String subject;
	private String attachment;

	public EmailDetails() {

	}

	public EmailDetails(final String sender, final String recipient, final String msgBody, final String subject,
			final String attachment) {
		this.sender = sender;
		this.recipient = recipient;
		this.msgBody = msgBody;
		this.subject = subject;
		this.attachment = attachment;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	@Override
    public String toString() {
        return "EmailDetails{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", msgBody='" + msgBody + '\'' +
                '}';
    }

}
