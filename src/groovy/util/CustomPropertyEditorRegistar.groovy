package util

import java.beans.PropertyEditorSupport;
import java.text.ParseException;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

class CustomPropertyEditorRegistar implements PropertyEditorRegistrar{
	
	def dateEditor
	
	@Override
	void registerCustomEditors(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(Date.class, dateEditor)
	}

}

class CustomDateEditor extends PropertyEditorSupport{
	
	boolean allowEmpty
	String[] formats
	
	void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !text) {
            // Treat empty String as null value.
            setValue(null)
        }
        else {
            try {
                setValue(DateUtils.parseDate(text, formats))
            }
            catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex)
            }
        }
    }

    /**
     * Format the Date as String, using the first specified format
     */
    String getAsText() {
        def val = getValue()
        val?.respondsTo('format') ? val.format(formats[0]) : ''
    }
}