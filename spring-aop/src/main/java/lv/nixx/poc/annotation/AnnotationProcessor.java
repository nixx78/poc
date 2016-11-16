package lv.nixx.poc.annotation;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;

import javax.tools.Diagnostic.Kind;
import javax.lang.model.element.TypeElement;


//@SupportedAnnotationTypes( "lv.nixx.poc.annotation.ProcessorAnnotation" )
//@SupportedSourceVersion( SourceVersion.RELEASE_8 )
public class AnnotationProcessor extends AbstractProcessor {
	
	public AnnotationProcessor() {
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Kind.NOTE, "AnnotationProcessor method process");
        
//	
//		System.out.println("Annotation processor");
//		final Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith( ProcessorAnnotation.class );
//		for (Element element : elementsAnnotatedWith) {
//			System.out.println(element);
//		}
		return true;
	}


}
