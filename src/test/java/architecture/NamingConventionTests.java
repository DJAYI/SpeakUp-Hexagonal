package architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
        packages = "com.electiva.speakup",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class NamingConventionTests {

    @ArchTest
    static final ArchRule controllers_should_reside_in_rest_adapter =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("Controller")
                    .should().resideInAPackage("..infrastructure.adapter.in..")
                    .because("Los controladores son adaptadores HTTP, viven en infrastructure.adapter.in.rest");

    @ArchTest
    static final ArchRule use_cases_should_reside_in_ports_in =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("UseCase")
                    .should().resideInAPackage("..application.port.in")
                    .because("Los casos de uso son puertos de entrada, viven en application.port.in");

    @ArchTest
    static final ArchRule in_ports_should_be_interfaces =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("UseCase")
                    .should().beInterfaces()
                    .because("Los puertos de entrada son contratos" +
                            "deben ser interfaces, no implementaciones");

    @ArchTest
    static final ArchRule out_ports_should_reside_in_ports_out =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("Port")
                    .should().resideInAPackage("..application.port.out")
                    .because("Los puertos de salida son interfaces para comunicarse con el mundo exterior" +
                            "deben vivir en application.port.out");

    @ArchTest
    static final ArchRule out_ports_should_be_interfaces =
            classes()
                    .that()
                    .haveSimpleNameEndingWith("Port")
                    .should().beInterfaces()
                    .because("Los puertos de salida son comportamiento del mundo exterior" +
                            "deben ser interfaces, no implementaciones");
}
