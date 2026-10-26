package architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
        packages = "com.electiva.speakup",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class HexagonalArchitectureTests {

    @ArchTest
    static final ArchRule domain_should_not_depend_on_infrastructure =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..infrastructure..")
                    .because("El dominio es el núcleo puro - no puede conocer la existencia " +
                            "de adaptadores ni configuraciones");

    @ArchTest
    static final ArchRule domain_should_not_depend_on_application =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..application..")
                    .because("El dominio es autosuficiente - no conoce use cases, comands ni services");

    @ArchTest
    static final ArchRule application_should_not_depend_on_infrastructure =
            noClasses()
                    .that().resideInAPackage("..application..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..infrastructure..")
                    .because("La capa de aplicación usa puertos (interfaces), nunca implementaciones concretas");


@ArchTest
    static final ArchRule domain_should_not_depend_on_spring =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage("org.springframework..")
                    .because("El dominio debe ser Java puro - testearlo no requiere de Spring context");

    @ArchTest
    static final ArchRule application_should_not_publish_events =
            noClasses()
                    .that().resideInAPackage("..application..")
                    .should().dependOnClassesThat()
                    .haveFullyQualifiedName("org.springframework.context.ApplicationEventPublisher")
                    .because("Publicar eventos es responsabilidad de la infraestructura; " +
                            "application solo registra eventos de dominio y el adapter los publica");
}
