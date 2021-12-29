package ruta3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ruta3.NaiveCoref.Person;

public class NaiveCorefTest {

    private AnalysisEngine analysisEngine;

    public NaiveCorefTest() {
    }

    @Before
    public void createEngine() throws Exception {
        analysisEngine = AnalysisEngineFactory.createEngine("ruta3.NaiveCorefEngine");
    }

    @After
    public void destroyEngine() throws Exception {
        analysisEngine.destroy();
    }

    @Test
    public void testFooBar() throws Exception {
        
        JCas inputJCas = analysisEngine.newJCas();

        // Populate CAS
        Path filePath = Paths.get(NaiveCorefTest.class.getClassLoader().getResource("ruta3/JohnDoe.txt").getFile());
        inputJCas.setDocumentText(new String(Files.readAllBytes(filePath), "UTF-8"));

        // Run AE
        analysisEngine.process(inputJCas);

        // Assert result
        AnnotationIndex<Person> peopleIndex = inputJCas.getAnnotationIndex(Person.class);
        assertEquals("Size of AnnotationIndex<Person>", 3, peopleIndex.size());

        // FIXME: This fails with Ruta 3.0.1 and Ruta 3.1.0
        peopleIndex.stream().forEach(p -> assertNotNull(String.format("Person name of '%s' is null", p.getCoveredText()), p.getName()));
    }

}
