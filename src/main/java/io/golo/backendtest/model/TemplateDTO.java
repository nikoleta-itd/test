package io.golo.backendtest.model;

/**
 * Represents a DTO.
 */
public class TemplateDTO {
	
	private String message;

    /**
     * Default constructor, empty for Json serializer
     */
    public TemplateDTO() {
        // Default constructor, empty for Json serializer
    }
    
    public TemplateDTO(String message) {
        this.message = message;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    
}
