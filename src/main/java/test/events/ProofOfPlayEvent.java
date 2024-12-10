package test.events;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class ProofOfPlayEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	
	private final long slideshowId;
	private final long imageId;

	public ProofOfPlayEvent(Object source, long slideshowId, long imageId) {
		super(source);
		this.slideshowId = slideshowId;
		this.imageId = imageId;
	}
}