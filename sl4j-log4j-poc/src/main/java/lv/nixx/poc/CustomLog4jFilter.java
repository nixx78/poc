package lv.nixx.poc;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;


@Plugin(name = "CustomFilter", category = Node.CATEGORY, elementType = Filter.ELEMENT_TYPE, printObject = true)
public class CustomLog4jFilter extends AbstractFilter {
	
	private String excludeString;

	private CustomLog4jFilter(final String exexcludeString, final Result onMatch, final Result onMismatch) {
        super(onMatch, onMismatch);
        this.excludeString = exexcludeString;
    }

    @Override
    public Result filter(final LogEvent event) {
    	
    	Message message = event.getMessage();
    	String formattedMessage = message.getFormattedMessage();
        
    	return formattedMessage.contains(excludeString) ? getOnMatch() : getOnMismatch();
    }


    @Override
    public String toString() {
        return "Filter";
    }


    @PluginFactory
    public static CustomLog4jFilter createFilter(
    		@PluginAttribute("excludeString") final String excludeString, 
    		@PluginAttribute("onMatch") final Result match,
            @PluginAttribute("onMismatch") final Result mismatch) {
    	
        final Result onMatch = match == null ? Result.NEUTRAL : match;
        final Result onMismatch = mismatch == null ? Result.DENY : mismatch;
        return new CustomLog4jFilter(excludeString, onMatch, onMismatch);
    }}
